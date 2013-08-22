package java_one_scala_demo

/**
 * Created with IntelliJ IDEA.
 * User: zhangyi
 * Date: 13-8-4
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
object ImplicitDemo {

  def implicitValueDemo(){

    println("\nimplicit value demo\n")

    trait Logger{def log(msg: String)}

    object Logger{
      implicit object DefaultLogger extends Logger{
        def log(msg: String) = println("DL>" + msg)
      }
      def log(msg: String)(implicit logger:Logger)={
        logger.log(msg)
      }
    }

    Logger.log("Hello, Implicit World~")

    class MyLogger extends Logger{ def log(msg:String) = println("ML>>" + msg)}

    implicit def MyLogger = new MyLogger

    Logger.log("Hello, Another Implicit World~")
  }

  def implicitFunctionDemo(){

    println("\nimplicit function demo\n")

    //val int1: Int = 3.7

    implicit def longToInt(d: Double):Int = d.toInt
    val int2: Int = 3.7
    println(int2)

  }

  def implicitClassDemo(){

    println(1 to 10)
    //1 --> 10

    implicit class RangeMaker(val left: Int){
      def -->(right: Int) = {
        left to right
      }
    }

    println(1 --> 10)
  }


  def main(args: Array[String]){

    implicitValueDemo()
    implicitFunctionDemo()
    implicitClassDemo()
  }

}
