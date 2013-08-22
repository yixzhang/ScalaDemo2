package ch10_testing_dependency_injection

/**
 * Created with IntelliJ IDEA.
 * User: zhangyi
 * Date: 13-8-20
 * Time: 下午6:02
 * To change this template use File | Settings | File Templates.
 */
class CalculatePriceService (
  val costPlusCalculator: Calculator,
  val externalPriceSourceCalculator: Calculator) {

  val calculators = Map(
    "costPlus" -> calculate(costPlusCalculator) _ ,
    "externalPriceSource" -> calculate(externalPriceSourceCalculator) _)

  def calculate(priceType: String, productId: String): Double = {
    calculators(priceType)(productId)
  }

  private[this] def calculate(c: Calculator)(productId: String):Double =
    c.calculate(productId)
}

trait Calculator {
  def calculate(productId: String): Double
}

class CostPlusCalculator extends Calculator{
  def calculate(productId: String): Double = {
    productId match {
      case "1" => 20.0
      case "2" => 30.0
      case "3" => 35.5
    }
  }
}

class ExternalPriceSourceCalculator extends Calculator{
  def calculate(productId: String): Double = {
    productId match {
      case "1" => 100.0
      case "2" => 300.0
      case "3" => 135.5
    }
  }
}
