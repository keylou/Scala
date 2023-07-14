object Main extends App {
  // Списки
  val digits = List(4, 2)
  // digits.head имеет значение 4
  // digits.tail все остальное
  def sum(lst: List[Int]): Int = lst match {
    case Nil => 0
    case h :: t => h + sum(t) // h - это lst.head, t - это lst.tail
  } //  :: разбивает список на голову и хвост
  println(sum(digits))

  // Множества (Set)
  // Не сохраняют порядок добавления элементов
  // Связанное хеш-множество сохраняет: val weekdays = scala.collection.mutable.LinkedHashSet("Mo", "Tu", "We", "Th", "Fr")
  // Cортированное множество: scala.collection.mutable.SortedSet(1, 2, 3, 4, 5, 6)

  // Общие методы
  // head, last, headOption, lastOption - Возвращают первый или последний элемент
  // tail, init - Возвращают все, кроме первого или последнего элемента
  // length, isEmpty
  // sum, product, max, min - сумма, произведение, макс, мин
  // count(pred), forall(pred), exists(pred)        pred - предикат
  // takeWhile(pred), dropWhile(pred)
  // map(f), foreach(f), flatMap(f), collect(pf)
  // slice(from, to) И ТАК ДАЛЕЕ

  val names = List("Peter", "Paul", "Mary")
  names.map(_.toUpperCase()) // List("PETER", "PAUL", "MARY")
  "-3+4".collect { case '+' => 1 ; case '-' => -1 } // Vector(-1, 1)

  // reduceLeft
  List(1, 7, 2, 9).reduceLeft(_ - _) // 1 - 7 - 2 - 9 = -17
  List(1, 7, 2, 9).reduceRight(_ - _) // 1 - (7 - (2 - 9)) = -13

  // fold
  List(1, 7, 2, 9).foldLeft(0)(_ - _)  // 0 - 1 - 7 - 2 - 9 = -19

  // (1 to 10).scanLeft(0)(_ + _) // Vector(0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55)

  // Потоки
  def numsFrom(n: BigInt): Stream[BigInt] = n #:: numsFrom(n + 1)
  // #:: создает потоки
  val tenOrMore = numsFrom(10)
  /*возвращается объект потока, который отображается как Stream(10, ?)
  Хвост потока не вычисляется. Если вызвать метод tenOrMore.tail.tail.tail возвращается Stream(13, ?)*/

 /* Методы потоков вычисляются лениво. Например, val squares = numsFrom(1).map(x => x * x) вернет Stream(1, ?)
  Чтобы получить следующий элемент, вам придется вызвать метод squares.tail.*/

  // squares.take(5).force вернет Stream(1, 4, 9, 16, 25).

  // view в коллекциях так же реализует отложенные вычисления
  val palindromicSquares = (1 to 1000000).view
    .map(x => x * x)
    .filter(x => x.toString == x.toString.reverse)
  palindromicSquares.take(10).mkString(",")
  // произведет много квадратов, пока не найдет десять палиндромов, после чего вычисления остановятся
  // В отличие от потоков вычисления не сохраняются. При повторном вызове вычисления пойдут заново.

  // ПАРАЛЛЕЛЬНОЕ ВЫЧИСЛЕНИЕ
  // Пусть coll - коллекция.
  // coll.par.sum - параллельно вычислит сумму элементов.
  // for (i <- (0 until 100000).par) print(s" $i") параллельный for

}
