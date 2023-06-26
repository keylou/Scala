import scala.collection.mutable._
object Main extends App{
  // Array для массивов фиксированной длины
  // Arraybuffer для массивов переменной длины
  // for (elem <- arr) для обхода элементов;
  // for (elem <- arr if ... ) ... yield ... для преобразования в новый массив;
  val nums = new Array[Int](10)
  // Массив с десятью целыми числами, инициализированными нулями
  val s = Array("Hello", "World")
  // Массив типа Array[String] с длиной 2 – тип выводится компилятором
  // Заметьте: при наличии начальных значений слово new не используется
  for (i <- s)
    println(i)
  s(0) = "Goodbye"
  // Массив Array("Goodbye", "World")
  for (i <- s)
    println(i)
  // ArrayBuffer аналог вектора в плюсах
  val b = ArrayBuffer[Int]()
  // Или new ArrayBuffer[Int]
  // Пустой буфер, подготовленный для хранения целых чисел
  b += 1
  // ArrayBuffer(1)
  // Добавление элемента в конец с помощью +=
  b += (1, 2, 3, 5)
  // ArrayBuffer(1, 1, 2, 3, 5)
  // Добавление в конец нескольких элементов, заключенных в скобки
  b ++= Array(8, 13, 21)
  // ArrayBuffer(1, 1, 2, 3, 5, 8, 13, 21)
  // С помощью оператора ++= можно добавить в конец любую коллекцию
  b.dropRightInPlace(5)
  // ArrayBuffer(1, 1, 2)
  // Удаление последних пяти элементов
  for (i <- b)
    print(s"$i ")
  println()
  b.insert(2, 6)
  // ArrayBuffer(1, 1, 6, 2)
  // Вставка элемента в позицию с индексом 2

  b.insert(2, 6)
  // ArrayBuffer(1, 1, 6, 6, 2)
  // Можно вставить любое количество элементов ??? выдает ошибку

  b.remove(2)
  // ArrayBuffer(1, 1, 6, 2)
  b.remove(2, 2)
  // ArrayBuffer(1, 1)
  // Второй параметр определяет количество удаляемых элементов
  for (i <- b)
    print(s"$i ")
  println()

  // если нужен массив с неизвестным количеством элементов, то буффер преобразовать в массив
  b.toArray
  // Array(1, 1)


  // ВАРИАНТЫ ОБХОДА
  // for (i <- b)
  // for (i <- b.reverse)
  // for (i <- 0 until b.length) until = length-1
  // for (i <- 0 until (b.length,2)) после запятой длина шага
  // for (i <- b.indices) по индексам
  // for (i <- b.indices.reverse) по индексам в обратную сторону


  val a = Array(2, 3, 5, 7, 11)
  val result = for (elem <- a) yield 2 * elem
  // результат: Array(4, 6, 10, 14, 22)
  // можно добавить ограничить в for (это будет if)
  val result2 = for (elem <- a if elem % 2 == 0) yield 2 * elem
  // a.filter(_ % 2 == 0).map(2 * _) аналогично
  val result3 = for (elem <- a if elem >= 0) yield elem
  val positionsToRemove = for (i <- a.indices if a(i) < 0) yield i

  // лучше не создавать новый буфер, а сохранить нужные элементы, а затем буфер укоротить
  val positionsToKeep = for (i <- a.indices if a(i) >= 0) yield i
  for (j <- positionsToKeep.indices) a(j) = a(positionsToKeep(j))
  a.dropRight(a.length - positionsToKeep.length)

  // ОСНОВНЫЕ МЕТОДЫ МАССИВА
  // sum max sorted
  val c = ArrayBuffer(1, 7, 2, 9)
  val cSorted = c.sorted // b не изменился; bSorted - это ArrayBuffer(1, 2, 7, 9)
  val cDescending = b.sortWith(_ > _) // ArrayBuffer(9, 7, 2, 1)
  val d = Array(1, 7, 2, 9)
  scala.util.Sorting.quickSort(d)
  // d – теперь Array(1, 2, 7, 9)
  // если потребуется вывести содержимое массива или буфера, метод mkString позволит указать разделитель для вывода между элементами
  d.mkString(" and ")  // "1 and 2 and 7 and 9"
  d.mkString("<", ",", ">") // "<1,2,7,9>"


  // TraversableOnce, Traversable и Iterable - некоторая коллекция, может встречать в описании метода
  // GenSeq и Seq являются типами упорядоченных коллекций, элементы которых следуют в некотором
  // определенном порядке. Считайте их массивом, списком или строкой.



  // Многомерные массивы
  val matrix = Array.ofDim[Double](3, 4) // Три строки, четыре столбца
  val triangle = new Array[Array[Int]](10) //строки разной длины
  for (i <- triangle.indices)
    triangle(i) = new Array[Int](i + 1)

  val one = ArrayBuffer[Int]()
  val n = 7
  for (i <- -5 until n) {
    one += i
  }

//  for (i <- 1 until (one.length,2)) {
//    val tmp = one(i)
//    one(i) = one(i-1)
//    one(i-1) = tmp
//  }

  val two = for (i <- 0 until one.length) yield {
    if (i == one.length-1) one(i)
    else if (i % 2==0) one(i+1)
    else one (i-1)
  }

  val OnlyPos = for (i <- 0 until one.length if (one(i) > 0)) yield one(i)
  val NotPos = for (i <- 0 until one.length if (one(i) <= 0)) yield one(i)
  val arr = OnlyPos ++ NotPos

  val three = Array(1,2,3,1,2,3,4,5,6)
  val threee = three.distinct

    println()
  for (i <- threee)
    print (s"$i ")
}
