package test.scala.ch10_testing

import org.scalacheck._

object StringSpecification extends Properties("String"){

  property("reverse of reverse gives you same string back") =
    Prop.forAll((a: String) => a.reverse.reverse == a)

  property("different string has different hashcode") =
    Prop.forAll {(x: String, y: String) =>
      (x != y) == (x.hashCode != y.hashCode)
    }
}