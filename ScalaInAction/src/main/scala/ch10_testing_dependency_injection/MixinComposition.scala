package ch10_testing_dependency_injection

object MixinComposition {

  def main(args: Array[String]){
    class Iter extends StringIterator("year") with RichIterator
    new Iter foreach println
  }
}

trait AbsIterator{
  type T
  def hasNext: Boolean
  def next: T
}

trait RichIterator extends AbsIterator{
  def foreach(f: T => Unit): Unit =
    while(hasNext) f(next)
}

class StringIterator(s: String) extends AbsIterator{
  type T = Char
  private var i = 0
  def hasNext = i < s.length
  def next = {val x = s.charAt(i); i = i+1; x}
}


