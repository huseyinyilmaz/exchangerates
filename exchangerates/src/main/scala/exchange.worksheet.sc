import java.time.ZonedDateTime
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter._

import java.time.format.DateTimeFormatter

//val dts = "2018-12-13T19:19:08.266120+00:00"

val dts = "2021-02-08T21:27:00+00:00"
val a = LocalDateTime.parse(dts, ISO_DATE_TIME)
val b = OffsetDateTime.parse(dts, ISO_OFFSET_DATE_TIME)
val c = ZonedDateTime.parse(dts, ISO_ZONED_DATE_TIME)


a
b.getOffset()
c.getZone()

//OffsetDateTime.parse(dts, ISO_DATE_TIME)

val iso8601DateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

//iso8601DateFormatter.format()


val st = """{"time":{"updated":"Feb 8, 2021 21:27:00 UTC","updatedISO":"2021-02-08T21:27:00+00:00","updateduk":"Feb 8, 2021 at 21:27 GMT"},"disclaimer":"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org","bpi":{"USD":{"code":"USD","rate":"44,223.2105","description":"United States Dollar","rate_float":44223.2105},"XBT":{"code":"XBT","rate":"1.0000","description":"Bitcoin","rate_float":1}}}""""
