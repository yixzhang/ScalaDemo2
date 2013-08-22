package future_demo

import scala.concurrent.{Await, future, promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object PromiseDemo {

  def main(args: Array[String]){

    //1. simple promise demo
    val p = promise[String]
    val f = p.future

    val producer = future {
      Thread.sleep(1000)
      p success "Message A"
    }

    val consumer = future {
      Thread.sleep(1000)
      f onSuccess{
        case r: String =>
          println("get " + r)
      }
    }

    Thread.sleep(2000)

    //2. composition
    val f1 = future {1}
    val p1 = promise[Int]

    p1 completeWith f1

    p1.future onSuccess{
      case x => print(x)
    }
  }

}
