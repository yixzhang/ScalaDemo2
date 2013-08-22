package ch10_testing_dependency_injection

/**
 * Created with IntelliJ IDEA.
 * User: zhangyi
 * Date: 13-8-22
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
object ImplicitParameters {

  class CalculatePriceService(
    implicit val costPlusCalculator: CostPlusCalculator,
    implicit val externalPriceSourceCalculator: ExternalPriceSourceCalculator
  ){
    val calculators = Map(
      "costPlus" -> calculate(costPlusCalculator) _ ,
      "externalPriceSource" -> calculate(externalPriceSourceCalculator) _)

    def calculate(priceType: String, productId: String): Double = {
      calculators(priceType)(productId)
    }

    private[this] def calculate(c: Calculator)(productId: String):Double =
      c.calculate(productId)
  }

  object ProductionService {
    implicit val costPlusCalculator = new CostPlusCalculator
    implicit val externalPriceSourceCalculator = new ExternalPriceSourceCalculator
  }

  object ProductionConfig {
    import ProductionService._
    val priceService = new CalculatePriceService

    def getPriceService = priceService
  }

  object FakeService{
    implicit val costPlusCalculator = new CostPlusCalculator{
      override def calculate(productId: String): Double = 0.0
    }
    implicit val externalPriceSourceCalculator = new ExternalPriceSourceCalculator{
      override def calculate(productId: String): Double = 0.0
    }
  }

  object FakeConfig{
    import FakeService._

    val priceService = new CalculatePriceService

    def getPriceService = priceService
  }

  def main(args: Array[String]){
    val productId = "1"
    val price = ProductionConfig.getPriceService.calculate("costPlus", productId)
    println("costPlus price of product " +  productId + " is " + price)
    val fakePrice = FakeConfig.getPriceService.calculate("externalPriceSource", productId)
    println("external source price of fake product " +  productId + " is " + fakePrice)

  }


}
