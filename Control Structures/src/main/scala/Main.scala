

object Main extends App {
  import scala.io.StdIn
  val x=1
  if (x==1) println(1) else println(0)
  val string = if (x==1) "1" else "0"
  println(string)
  println(if (x==1) "1" else "0")
  var i=0
  while (i<=10)
    {
      //print(i)
      i+=1
    }
  for (i <- 1 to 10; j<-i to 10)
    {
      //println(s"$i $j")
    }
  for (i <- 1 to 10; j <- 1 to 10 if i > j) {
    //println(s"$i $j")
  }

  //for (i <- 1 until 10) println(i) НЕ включая 10
  //for (i <- Range.inclusive(1, 10)) println(i) Включая 10
  //for (i <- Range(1, 10)) println(i) НЕ включая 10

  val n = StdIn.readInt()
  for (i <- 1 until n; j<-1 until n) {
    val ii: BigInt = i
    val jj: BigInt = j
    if (ii.gcd(j)==1)
      println(s"$i $j")
  }
}
