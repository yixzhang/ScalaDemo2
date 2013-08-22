package future_demo

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.Success
import scala.util.Failure

object SimpleFutureDemo {

  def main(array: Array[String]){
    val startTime = System.nanoTime();
    val future1 = future(timeTakingFunctionReturnsException(1))

    future1 onComplete{
      case Success(num) =>
        val elapsedTime = (System.nanoTime() - startTime)/ 1000000
        println("future 1 returns "+ num + " ,finished in " + elapsedTime + " ms")
      case Failure(t) =>
        println("An error has occured: " + t.toString)
    }

    Thread.sleep(4000)
  }

  def timeTakingFunction(number: Int): Int = {
    Thread.sleep(3000)
    number
  }

  def timeTakingFunctionReturnsException(number: Int): Int = {
    Thread.sleep(2000)
    throw new RuntimeException("It's a new runtime exception")
  }
}
