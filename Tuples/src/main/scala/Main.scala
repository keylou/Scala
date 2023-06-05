object Main extends App {
  val ingredient = ("Sugar", 25) // кортеж
  val nameAndPopulation = "Moscow" -> 12e6 // пара
  val intAndString: (Int, String) = 1 -> "one"
  val stringAndInt: (String, Int) = intAndString.swap

  def euclid (a: Int, b:Int ): (Int, Int) = {
    if (b > a) euclid(b,a).swap
    else if (b==0) (1,0)
    else {
      val d = a/b
      val r = a%b
      val (x,y) = euclid(b,r)
      (y,x-d*y)
    }
  }
  println(euclid (7,4))

  def swap3(tuple: (Int, Int, Int)): (Int, Int, Int) = {
    (tuple._3, tuple._2, tuple._1)
  }
}
