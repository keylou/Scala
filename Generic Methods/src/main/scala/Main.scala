object Main extends App{
  def ifThenElseInt(cond: Boolean)
                   (t: => Int, e: => Int): Int =
    if(cond) t else e
  def ifThenElseString(cond: Boolean)
                      (t: => String, e: => String): String =
    if (cond) t else e

  def ifThenElseLong(cond: Boolean)
                    (t: => Long, e: => Long): Long =
    if (cond) t else e

  def ifThenElse[A](cond: Boolean, t: => A, e: => A): A = // обобщенный метод
    if (cond) t else e
  println(ifThenElse[String](1 > 2, "one", "two"))

  def combineOn[A, B](comb: (B, B) => B)
                     (f: A => B, g: B => A)
                      : (A, A) => A =
    (x, y) => g(comb(f(x), f(y)))
  // комбинация типов А и В, функция comb из двух типов В получает В(сумма для интов)
  // ф-ция f из типа А получает В, ф-ция g наоборот.
  // сам метод из двух типов А получает А
  // из передаваемых х,у получаем тип А из комбинации двух типов В

  val sumStrings =
    combineOn[String, Int](_ + _)(_.toInt, _.toString) // [A,B](comb)(f,g)
  println(sumStrings("123", "32")) //: String = "155"

  def calc42M[A]: (Int => A) => A = f => f(42000000)
  calc42M(i => s"number is $i")

  def tailRec[A, B](iter: A => A,
                    comb: (B, A) => B,
                    cond: A => Boolean)(start: A, init: B): B = {
    def go(x: A, acc: B): B =
      if (cond(x)) go(iter(x), comb(acc, x)) else acc
    go(start, init)
  }
  println(calc42M(n =>
  tailRec[Int, Long](_ - 1, _ + _, _ >= 0)(n,0)
  ))
}
