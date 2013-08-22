package ch4_functional_data_structure

/**
 * Created with IntelliJ IDEA.
 * User: zhangyi
 * Date: 13-8-8
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */


sealed abstract class Maybe[+A]{
  def isEmpty: Boolean
  def get: A
}

case class Just[A](value: A) extends Maybe[A]{
  def isEmpty = false
  def get = value
}

case object Nil extends Maybe[Nothing]{
  def isEmpty = true
  def get = throw new NoSuchElementException("Nil.get")
}

object Position {

  def position[A](xs: List[A], value: A): Maybe[Int] = {
    val index = xs.indexOf(value)
    if(index != -1) Just(index) else Nil
  }

}
