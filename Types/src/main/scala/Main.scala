//Целые Числа Byte, Short, Int, Long
//Дробные числа Float, Double
//Символы Сhar
//Булевые значения Boolean
//Единичный тип Unit принимает одно значeние, как правило знать не нужно,
//используем как возвращаемый тип (наверно типо проверки, что функция проработала)
// AnyRef - любой ссылочный тип
// AnyVal - любой примитивный тип
// Any - любой тип

object Main extends App{
  val message: String = "Hello, Scala!"
  val message2: Unit = message
  val message3: Unit = {
    val x=1.0
    Math.acos(x)
  }
  val x: Int = 42
  println(message2)
  println(message3)
}


import scala.math._
object R extends App {
  def normalDistribution(mu: Double, sigma: Double, x: Double): Double = {
    val p = exp(-(x - mu) * (x - mu) / (2.0 * sigma * sigma))
    1.0 / (sigma * sqrt(2.0 * Pi))*p
  }
}