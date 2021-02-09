package exchangerates.exchange

import scala.io.Source

import scala.util.parsing.json.JSON

import java.time.format.DateTimeFormatter
import java.time.OffsetDateTime

case class Currency(val code: String, val description: String)

case class CurrencyValue(
    val code: String,
    val description: String,
    val rate: Double
) {
  def this(vals: Map[String, Any]) = {
    this(
      code = vals("code").asInstanceOf[String],
      description = vals("description").asInstanceOf[String],
      rate = vals("rate_float").asInstanceOf[Double]
    )
  }
}

case class CurrencyResult(
  val code: String,
  val updatedTime: OffsetDateTime,
  val disclaimer: String,
  val usdValue: CurrencyValue,
  val currencyValue: CurrencyValue
)

object client {
  val supportedCurrenciesURL =
    "https://api.coindesk.com/v1/bpi/supported-currencies.json"
  val currentPriceURL = "https://api.coindesk.com/v1/bpi/currentprice/%1$s.json"
  def getCurrencyList() = {
    val s = Source.fromURL(supportedCurrenciesURL).mkString
    // val s = Source
    //   .fromFile(
    //     "/Users/huseyin/projects/exchangerates/exchangerates/src/main/resources/currencyList.json"
    //   )
    //   .mkString
    JSON.parseFull(s) match {
      case Some(json) =>
        json.asInstanceOf[List[Map[String, String]]] map (j =>
          Currency(j("currency"), j("country"))
        )
      case None => {
        println("Cannot parse response: " + s)
        List()
      }
    }
    // val currencies: Option[Vector[Currency]] =
    // println(currencies)
  }

  def getTime(dt: String) = {
    OffsetDateTime.parse(dt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
  }

  def getCurrentPrice(code: String): Option[CurrencyResult] = {
    val url = currentPriceURL.format(code)
    val s = Source.fromURL(url).mkString
    // val s = Source
    //   .fromFile(
    //     "/Users/huseyin/projects/exchangerates/exchangerates/src/main/resources/currencyDetail.json"
    //   )
    //   .mkString

    for {
      map <- JSON.parseFull(s).asInstanceOf[Option[Map[String, Any]]]

      timeMap <- map.get("time").asInstanceOf[Option[Map[String, String]]]
      isoTime <- timeMap.get("updatedISO")
      updatedTime = getTime(isoTime)
      // updateTimeValue = new UpdateTime(timeMap)
      disclaimer <- map.get("disclaimer")

      valueMap <- map.get("bpi").asInstanceOf[Option[Map[String, Any]]]

      usdMap <- valueMap.get("USD").asInstanceOf[Option[Map[String, Any]]]
      usdValue = new CurrencyValue(usdMap)

      currencyMap <- valueMap.get(code).asInstanceOf[Option[Map[String, Any]]]
      currencyValue = new CurrencyValue(currencyMap)
    } yield new CurrencyResult(
      code = code,
      updatedTime = updatedTime,
      disclaimer = disclaimer.asInstanceOf[String],
      usdValue = usdValue,
      currencyValue = currencyValue
    )
  }
}
