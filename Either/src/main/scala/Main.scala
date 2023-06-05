import java.lang.Math.sqrt

object Main extends App {
  val numOrStr1: Either[Double, String] = Left(2.12) // выбираем, что нам нужно или что есть
  val numOrStr2: Either[Double, String] = Right("Scala") // либо лево, либо право

  def info(numOrStr: Either[Double, String]): String =
    numOrStr match {
      case Left(num) => s"number $num"
      case Right(str) => s"string $str"
    }

  //def sqrt(x: Double): Either[String, Double] =
  //  if (x < 0) Left("negative number")
  //  else Right(Math.sqrt(x))

  //sqrt(7).getOrElse(0)
  //sqrt(3).filterOrElse(_ > 2, "too small")
  //sqrt(7).map(_.toString)
  //sqrt(7).flatMap(x => sqrt(x))

  def fun (x:Double, y: Double) =
    (sqrt(x) + sqrt(y)) / sqrt(x+y)

  println(fun(4, 5))

  def sqrtE(x: Double): Either[String,Double] =
    if(x<0) Left(s"$x < 0!")
    else Right(sqrt(x))
  def divE (x: Double, y: Double): Either[String,Double] =
    if(y==0) Left(s"zero division!")
    else Right(x/y)

  def funE (x: Double, y: Double): Either[String,Double] =
    sqrtE(x).flatMap{sx =>
      sqrtE(y)flatMap{sy =>
        sqrtE(x+y).flatMap{sxy =>
          divE(sx+sy,sxy)
        }}}
  println(funE(4,-5))
}
