//1. Hello World
println("Hello, World!")

//2. Statement Pop Quiz
var x = 1
val quiz = while(x < 10){
  println("x is:" + x)
  x += 1
}


















//3. collection
val list = List(1, 2, 3)
val result1 = list.map(x => x * 2).reduceLeft((a , b)=> a + b)
val result2 = list.map(_ * 2).reduceLeft(_ + _)

//4. pattern matching 1
def len[A](v:List[A], l:Int = 0): Int = v match{
  case h::t => len(t, l+1)
  case Nil => l
}

len(List(1,2,3,4,5,6,7,8))

//5. pattern matching 2
trait Animal;
case class Dog(name: String, age: Int) extends Animal
case class Human(name: String, age: Int, sex: Boolean) extends Animal
case class Walrus(name: String, age: Int) extends Animal

def convertedAge(a: Animal):Int = a match{
  case Dog(name, age) => age * 7
  case Human(_, age, _) => age
  case Walrus("Donny", age) => age / 10
  case Walrus(name, age) if name == "Walter" => age
  case _ => 0
}

convertedAge(new Dog("Mike", 3))
convertedAge(new Human("Mike", 3, true))
convertedAge(new Walrus("Donny", 30))
convertedAge(new Walrus("Walter", 3))
convertedAge(new Walrus("Mike",3))









