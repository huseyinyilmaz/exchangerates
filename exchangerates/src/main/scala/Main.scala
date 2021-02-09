package exchangerates

import exchangerates.parser
import exchangerates.exchange


object Main extends App {

  def list(config: parser.Config) = {
    val currencies = exchange.client.getCurrencyList()
    println("=" * 80)
    currencies.map(c => println(c.code + " : " + c.description))
  }

  def get(config: parser.Config) = {
    exchange.client.getCurrentPrice(config.targetCurrency) match {
      case Some(currency) =>
        val usd = currency.usdValue
        val c = currency.currencyValue
        println("=" * 80)
        println(s"Code = ${currency.code}")
        println(s"Update Time = ${currency.updatedTime}")
        println("=" * 80)
        println(s"${c.code} - ${c.description}: ${c.rate}")
        println(s"${usd.code} - ${usd.description}: ${usd.rate}")
        println("=" * 80)
        println(s"Disclaimer = ${currency.disclaimer}")
        println("=" * 80)
        // println(currency)
      case None =>
        println("Could not get value.")
    }
  }


  def run(config: parser.Config) = {
    import parser.SubCommands._
    config.mode match {
      case ListSubCommand => list(config)
      case GetSubCommand => get(config)
    }
  }

  // start interface
  parser.cliParser.getCliArguments(args) match {
    case Some(config: parser.Config) => run(config)
    case None =>
  }
  sys.exit(0)
}
