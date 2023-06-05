
import scala.io.StdIn.readLine
object Main extends App {
val name = "Oleg"
  val greet = s"Hello $name!" // интерполяция, s перед ковычками дает возможность в строку вставлять значение переменной
  println(greet)
  val s =
    """|переносы
       |строк
       |не
       |потеряны
   """.stripMargin
  println(s)
  val c = "aaabbb"
  c.startsWith("aa")
  c.endsWith("bb")
  c.contains("ab")

  val x = BigInt(2).pow(10000).toString()
  val reg = "(.)\\1\\1".r // регулярыне выражения - шаблон для поиска данных, задаваемый в виде текстовой строки
  println(reg.findFirstIn(x))
}
