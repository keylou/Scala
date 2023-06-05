object Main extends App {
  def plusOne(number: Int): Int = number + 1
  println(plusOne(4))
  def sixty: Int = 10 * 6
  println(sixty)
  def plusAndPrint(x: Int, y: Int): Int = {
    val result = x + y
    println(s"$x + $y = $result")
    result
  }
  plusAndPrint(2, 3)
  def plusMul(q: Int, x: Int, y: Int): Int = {
    def mul(u: Int) = q * u
    mul(x) + mul(y)
  }
  def sumAllTimes(u: Int, nums: Int*): Int = u * nums.sum // nums - повторяемый параметр, можем передать список, вместо одного параметра
  println(sumAllTimes(3, 1, 2, 3))
  def plus3(x: Int, y: Int = 0, z: Int = 0): Int =
    100 * x + 10 * y + z
  def replaceNegative(x: Int, z: => Int): Int = // => передача по имени. Используется, когда этот парамерт вычислить трудозатратно
    if (x >= 0) x else z // Будет вычисляться только в случае, когда нужно будет его использовать
  def replaceNegative2(x: Int)(z: => Int): Int = // передача блока
    if (x >= 0) x else z
  println(replaceNegative2(1){ // в зависимости от результата if будет/не будет выполняться тело вызова
    println("calculated")
    3 * 3 * 3
  })
  def sumRange(from: Int, to: Int): Int = //рекурсия
    if (to < from) 0
    else from + sumRange(from + 1, to)
  println(sumRange(1, 10))
  def sumRange(from: Int, to: Int, acc: Int = 0): Int = // хвостовоя рекурсия, когда последний шаг - вызов функции
    if (to < from) acc
    else sumRange(from + 1, to, acc + from)
}
