package ch10_testing_dependency_injection

import scala.language.reflectiveCalls

object StructuralTyping {

  type Calculators = {
    val costPlusCalculator: Calculator
    val externalPriceSourceCalculator: Calculator
  }

  class CalculatePriceService(c: Calculators){
    val calculators = Map(
      "costPlus" -> calculate(c.costPlusCalculator) _,
      "externalPriceSource" -> calculate(c.externalPriceSourceCalculator) _
    )

    def calculate(priceType: String, productId: String): Double = {
      calculators(priceType)(productId)
    }

    private[this] def calculate(c: Calculator)(productId: String): Double = {
      c.calculate(productId)
    }
  }

  object ProductionConfig{
    val costPlusCalculator = new CostPlusCalculator
    val externalPriceSourceCalculator = new ExternalPriceSourceCalculator
    val priceService = new CalculatePriceService(this)

    def getPriceService = priceService
  }

  object TestConfig {
    val costPlusCalculator = new CostPlusCalculator{
      override def calculate(productId: String): Double = 0.0
    }
    val externalPriceSourceCalculator = new ExternalPriceSourceCalculator{
      override def calculate(productId: String): Double = 0.0
    }
    val priceService = new CalculatePriceService(this)

    def getPriceService = priceService
  }

  def main(args: Array[String]){
    val productId = "1"
    val price = ProductionConfig.getPriceService.calculate("costPlus", productId)
    println("costPlus price of product " +  productId + " is " + price)
    val fakePrice = TestConfig.getPriceService.calculate("externalPriceSource", productId)
    println("external source price of fake product " +  productId + " is " + fakePrice)
  }
}
