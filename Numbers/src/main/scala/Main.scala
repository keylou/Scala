
object Main extends App {
val a = BigInt(4348273694414124124L) + 4348273694414124124L
  println(a)
  println(a.gcd(128))
  println(a.toString(16))
  val b = "123123123".toInt + 2
  println(b)
  val c = 1.0
  // println(c+a) нельзя, Scala не понимает, отбрасывать или добавлять лишние разряды
}