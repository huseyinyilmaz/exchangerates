package exchangerates.parser

import java.io.File

object SubCommands extends Enumeration {
  type SubCommand = Value
  val ListSubCommand, GetSubCommand = Value
}

import SubCommands._

case class Config(
  val debug: Boolean = false,
  val mode: SubCommand = ListSubCommand,
  val targetCurrency: String = "",
  val baseCurrency: String = "",
) {
  override def toString() = "Config(mode=" + mode + ", target=" + targetCurrency + ", base=" + baseCurrency + ")"

}


object cliParser {
  def getCliArguments(args: Array[String]) = {
    import scopt.OParser
    val builder = OParser.builder[Config]
    val parser = {
      import builder._
      OParser.sequence(
        programName("exchangerates"),
        head("exchangerates", "1.0"),
        opt[Unit]("debug")
          .hidden()
          .action((_, c) => c.copy(debug = true))
          .text("this option is hidden in the usage text"),
        help("help").text("prints this usage text"),
        note("Prints out exchange rates for different currencies." + sys.props("line.separator")),
        cmd("list")
          .action((_, c) => c.copy(mode = ListSubCommand))
          .text("Prints all available currencies."),
        cmd("get")
          .action((_, c) => c.copy(mode = GetSubCommand))
          .text("Prints currency value in bitcoin.")
          .children(
            arg[String]("target")
              .required()
              .action((value, c) => c.copy(targetCurrency = value))
              .text("target currency"),
            arg[String]("base")
              .action((value, c) => c.copy(baseCurrency = value))

            .optional()
              .text("base currency"),
            checkConfig(c =>
              if (false && c.targetCurrency == c.baseCurrency) failure("target currency cannot be same with base currency")
              else success
            )
          )
      )
    }
    OParser.parse(parser, args, Config())
  }
}
