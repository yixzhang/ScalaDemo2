package ch10_testing_dependency_injection

object AbstractTypeMembers {

  def main(args: Array[String]){
    val cell = new AbsCell {
      type T = Int
      val init = 1
    }
    println("get cell: " + cell.get)
    println("set cell to 2")
    cell.set(2)
    println("get cell: " + cell.get)
  }

}

abstract class AbsCell {
  type T
  val init: T
  private var value: T = init
  def get: T = value
  def set(x: T): Unit = value = x
}


