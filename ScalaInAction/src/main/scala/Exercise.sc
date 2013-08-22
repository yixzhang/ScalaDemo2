
val a = 10
implicit class RangeMaker(val left: Int) extends AnyVal{
  def --> (right: Int)  = left to right
}
1 --> a


def random(array: Array[Int]){

  import  scala.util.Random
  val rand = new Random()
  val priorityArray : Seq[Int] = for(i <- 1 to array.length) yield  rand.nextInt()
  for(i <- 1 to priorityArray.length){

  }
}

def insert_sort_array_by_priority_array(array: Array[Int], priorityArray: Array[Int]){
  if(array.length >= 1){
    for(insert_num <- 1 to array.length -1){
      val value = priorityArray(insert_num)
      var i = insert_num - 1
      while( i > 0 && priorityArray(i) > value){
        array(i + 1) = array(i)
        i = i -1
      }
      array(i+1) = insert_num
    }
  }
}


