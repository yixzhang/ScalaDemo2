package ch10_testing_dependency_injection

import scala.Predef._


/**
 * the cake pattern is built on 3 abstract techniques:
 * Abstract members
 * Self type annotation
 * Mixin composition
 * TODO need to understand all of them
 */

trait Calculators {
  val costPlusCalculator: CostPlusCalculator
  val externalPriceSourceCalculator: ExternalPriceSourceCalculator

  trait Calculator {
    def calculate(productId: String): Double
  }

  class CostPlusCalculator extends Calculator {
    def calculate(productId: String): Double = {
      productId match {
        case "1" => 20.0
        case "2" => 30.0
        case "3" => 35.5
      }
    }
  }

  class ExternalPriceSourceCalculator extends Calculator {
    def calculate(productId: String): Double = {
      productId match {
        case "1" => 100.0
        case "2" => 300.0
        case "3" => 135.5
      }
    }
  }

}

trait CalculatePriceServiceComponent {
  this: Calculators =>

  class CalculatePriceService {
    val calculators = Map(
      "costPlus" -> calculate(costPlusCalculator) _,
      "externalPriceSource" -> calculate(externalPriceSourceCalculator) _)

    def calculate(priceType: String, productId: String): Double = {
      calculators(priceType)(productId)
    }

    private[this] def calculate(c: Calculator)(productId: String): Double =
      c.calculate(productId)
  }

}

object PricingSystem extends CalculatePriceServiceComponent with Calculators {
  val costPlusCalculator = new CostPlusCalculator
  val externalPriceSourceCalculator = new ExternalPriceSourceCalculator

  val calcService = new CalculatePriceService

  def getPrice(productId: String): Double = {
    calcService.calculate("costPlus", productId) +
    calcService.calculate("externalPriceSource", productId)
  }
}

object FakePricingSystem extends CalculatePriceServiceComponent with Calculators{
  class FakeCostPlusCalculator extends CostPlusCalculator{
    override def calculate(productId: String): Double = 10.0
  }

  class FakeExternalPriceSourceCalculator extends ExternalPriceSourceCalculator{
    override def calculate(productId: String): Double = 50.0
  }

  val costPlusCalculator = new FakeCostPlusCalculator
  val externalPriceSourceCalculator = new FakeExternalPriceSourceCalculator

  val calcService = new CalculatePriceService

  def getPrice(productId: String): Double = {
    calcService.calculate("costPlus", productId) +
      calcService.calculate("externalPriceSource", productId)
  }
}

object CakePattern{
  def main(args: Array[String]){
    val productId = "1"
    val price = PricingSystem.getPrice(productId)
    println("price of product " +  productId + " is " + price)
    val fakePrice = FakePricingSystem.getPrice(productId)
    println("price of fake product " +  productId + " is " + fakePrice)
  }
}






