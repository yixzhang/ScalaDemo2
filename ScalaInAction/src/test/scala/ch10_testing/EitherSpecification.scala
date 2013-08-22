package ch10_testing

import org.scalacheck.{Prop, Gen, Properties}

object EitherSpecification extends Properties("Either"){
  import Gen._
  import org.scalacheck.Arbitrary.arbitrary

  val leftValueGenerator  =  arbitrary[Int].map(Left(_))
  val rightValueGenerator =  arbitrary[Int].map(Right(_))
  implicit val eitherGenerator = oneOf(leftValueGenerator, rightValueGenerator)

  property("isLeft or isRight not both") =
    Prop.forAll((e: Either[Int, Int]) => e.isLeft != e.isRight)

  property("left value") =
    Prop.forAll((n:Int) => Left(n).fold(x => x, b => sys.error("fail")) == n)

  property("right value") =
    Prop.forAll((n:Int) => Right(n).fold(b => sys.error("fail"), x => x) == n)

  property("swap values") =
    Prop.forAll((e: Either[Int, Int]) => e match{
      case Left(a)  => e.swap.right.get == a
      case Right(b) => e.swap.left.get  == b
    })
}
