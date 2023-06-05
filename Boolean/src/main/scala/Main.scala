object Main extends App{
  val x = "Ka"
  val y = "te"
  val a = x + y
  val b = x + y
  println(a == b) // true сравнение по значению
  println(a eq b) // false сравнение по ссылке!

  val s3 = "bar"; val s1 = "foo" + s3; val s2 = "foo" + s3; println(s1 eq s2)
}
