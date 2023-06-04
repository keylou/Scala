import scala.io.StdIn.readInt
object Main extends App
{
  val x = 2+p//val не дает в будущем менять значение переменной, вычисляется сразу
  def y = 2+p//с def переменная будет вычисляться каждый раз, когда мы на них ссылаемся
  lazy val z = 2+p//будет вычисленно только в первый раз
  var p = 0//настоящая переменная
  println(x + " " + y + " " + z)
  p = 1
  println(x + " " + y + " " + z)
  // ссылаться на val можно только после их инициализации
  // ссылаться на def и lazy val можно до их инициализации
  // указание типа после имени переменной нужно писать ":"
  val out = "outer";
  {
    val out = "inner"

    println(out)
  }

  val a = readInt()
  val b = readInt()
  println(a - b)
  println(a*b)
}