# exchangerates
CLI command to get exchange rates.

```
huseyin@Huseyins-MBP scala-2.13 % scala exchangerates-assembly-1.0.jar --help
exchangerates 1.0
Usage: exchangerates [list|get] [options] <args>...

  --help                   prints this usage text
Prints out exchange rates for different currencies.

Command: list
Prints all available currencies.
Command: get target [base]
Prints currency value in bitcoin.
  target                   target currency
  base                     base currency
huseyin@Huseyins-MBP scala-2.13 %
```

```
huseyin@Huseyins-MBP scala-2.13 % scala exchangerates-assembly-1.0.jar get EUR
================================================================================
Code = EUR
Update Time = 2021-02-09T03:05Z
================================================================================
EUR - Euro: 37799.3766
USD - United States Dollar: 45661.6232
================================================================================
Disclaimer = This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org
================================================================================
huseyin@Huseyins-MBP scala-2.13 %
```

## How to build
(I am new to scala so if you know a better way of building this please open an issue.)

```
# to create a fat jar
$ sbt assembly
# to run
$ scala target/scala-2.13/exchangerates-assembly-1.0.jar get EUR
```
