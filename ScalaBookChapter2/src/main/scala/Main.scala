import scala.io.StdIn
import scala.math.sqrt

object Main extends App {

  val forlist = List(1,2,3,4,5)
  for (i <- forlist) // ура итератор
    print(i)
  println()



  val s = "Hello"
  var sum = 0
  for (i <- 0 to s.length - 1) // метод длины строки
    sum += s(i)
  println(sum)
  // same as
  sum = 0
  for (ch <- "Hello")
    sum += ch
  println(sum)



  for (i <- 1 to 3; j <- 1 to 3 if i != j) print(f"${10 * i + j}%3d") // несколько переменных меняется сразу
  for (i <- 1 to 3; from = 4 - i; j <- from to 3) print(f"${10 * i + j}%3d") // определяем переменную from, от которой только зависит другая переменная


  for (i <- 1 to 10) yield i % 3
  // Вернет Vector(1, 2, 0, 1, 2, 0, 1, 2, 0, 1)
  // инструкция yield -> цикл будет конструировать коллекцию


  def fac(n: Int) = {
    var r = 1
    for (i <- 1 to n) r = r * i
    r
  } //функция
    // в Scala допускается использование return для принудительного завершения, однако избегается.
    def fac2(n: Int): Int = if (n <= 0) 1 else n * fac(n - 1) // рекурсивная -> обазательно нужно указать тип возвращаемого значения

  def decorate(str: String, left: String = "[", right: String = "]") =
    left + str + right // можно указать значения по умолчанию

  def sum(args: Int*) = { // Int* позволяет передавать сколь угодное количество переменных
    var result = 0
    for (arg <- args) result += arg
    result
  }
  // вызов подобной функции val s = sum(1, 4, 9, 16, 25)
  // val s = sum(1 to 5: _*) Интерпретировать 1 to 5 как последовательность аргументов
  def recursiveSum(args: Int*): Int = { //head - начальный элемент, tail - все остальные
    if (args.length == 0) 0
    else args.head + recursiveSum(args.tail: _*)
  }



  //В Scala существуют процедуры - не имеют возвращаемого значения, не требуют написания = перед телом функции
  def box(s: String) { // Смотрите внимательнее: здесь нет =
    val border = "-" * (s.length + 2)
    print(f"$border%n|$s|%n$border%n")
  }
  // Можно писать def box(s : String): Unit = {...} Это то же самое



  // var - изменяемая переменная, val - неизменяемая переменная
  // lazy val - неизменяемая переменная, которая не будет рассчитана до обращения к ней

  // val words = scala.io.Source.fromFile("/usr/share/dict/words").mkString
  // Вычисляется немедленно, в момент определения words
  // lazy val words = scala.io.Source.fromFile("/usr/share/dict/words").mkString
  // Вычисляется при первом обращении к words
  // def words = scala.io.Source.fromFile("/usr/share/dict/words").mkString
  // Вычисляется всякий раз, когда происходит обращение к words


  var x = 10
  val f = if (x >= 0) {
    sqrt(x)
  } else throw new IllegalArgumentException("x should not be negative") // исключение
    // аналогия критических секций try catch

    // Инструкция try/finally позволяет освободить занятые ресурсы независимо от наличия или отсутствия исключения.
  // val in = new URL("http://horstmann.com/fred.gif").openStream()
  //try {
  // process(in)
  //} finally {
  // in.close()
  //}
  // Предложение finally выполнится независимо от того, возбудила ли исключение функция process. Поток чтения всегда будет закрыт.
  // Инструкция try/catch обрабатывает исключения, а инструкция try/finally предпринимает некоторые действия
  // (обычно – освобождение ресурсов), если исключение не будет обработано.
  // Их можно объединить в одну инструкцию try/catch/finally:


//  x = StdIn.readLine().toInt
//  val signum = if (x>0) {1}
//  else if (x<0) {-1}
//  else 0
//  println(signum)

  def countdown(n: Int): Unit = {
    for (i <- (0 to n).reverse)
      print(s" $i ")
  }
  countdown(6)

  var unisum = 1L
  for (i <- s)
    unisum *= i.toInt
  println(unisum)

  def product(s: String):Long = {
    if (s.length == 0) 1
    else s.head.toInt * product(s.tail)
  }
  println(product(s))

  def product(x: Double, n: Int): Double = {
    if (n > 0 & n % 2 ==0) product(x*x, n/2)
    else if (n > 0 & n % 2 !=0) x * product(x,n-1)
    else if (n==0) 1
    else product(1.0/x,-n)
  }
  println(product(2,-2))
}
