package future_demo

import scala.util.{Failure, Success, Random}
import scala.concurrent._
import ExecutionContext.Implicits.global

object ComplicatedFutureDemo {

  def main(args: Array[String]){
    val r1 = future(getRandomValue)
    val r2 = future(getRandomValue)
    val r3 = future(getRandomValue)

    r1 onSuccess{case v: Int => println("Random value is " + v)}
    r2 onSuccess{case v: Int => println("Random value is " + v)}
    r3 onSuccess{case v: Int => println("Random value is " + v)}

    val sumPositiveValue = for{
      rv1 <- r1.withFilter(r => r > 0).recover{ case _ => 0 }
      rv2 <- r2.withFilter(r => r > 0).recover{ case _ => 0 }
      rv3 <- r3.withFilter(r => r > 0).recover{ case _ => 0 }
    } yield rv1.toLong + rv2.toLong + rv3.toLong

    sumPositiveValue onComplete{
      case Success(sum) => println("The sum of all positive values is " + sum)
      case Failure(t) => println("A error occurs: " + t.toString)
    }

    Thread.sleep(1000)
  }

  def getRandomValue(): Int = {
     val randomGenerator: Random = new Random
     Random.nextInt()
  }
}

