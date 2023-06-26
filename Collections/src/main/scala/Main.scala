import scala.collection.immutable._
import scala.io.StdIn.{readInt, readLine}
import scala.util.Random //неизменяемые коллекции
// List[+A], Vector[+A], Stream[+A], Set[+A], Map[K, +V]

import scala.collection.mutable._ //изменяемые коллекции
// Buffer[A], Set[A], Map[A, B], Builder[-E, +С]

import scala.collection._ // общие надтипы
// Seq[+A], Set[+A], Map[K, +V], Iterator[+A]

// Array, string - специализированные коллекции
object Main extends App {
  val ints = Array(1, 2, 3, 5)
  ints(2) // 3
  ints(2) = 6
  ints(2) // 6

  val language = "Scala"
  val platform = "Stepik"
  val course = language + " " + platform
  val cource1 = s"$language $platform"
  val char: Char = course(3) // 'l'

  // Buffer[A] Саморастущий массив
  // Set[A] набор уникальных элементов
  // Map[A,B] ключ-значение
  // Builder[E,Coll] Промежуточный накопитель для построения коллекции

  //val strings = Buffer[String]()
  //strings += "scala"
  //strings += "+"
  //strings += "stepik"
  //strings += "="
  //strings += "love"
  //strings.mkString(" ")

  // List[A] Связный конечный список
  //Легко добавить элемент в начало
  // Stream[A] Ленивый связный список, возможно бесконечный
  //Легко добавить элемент в начало
  // Vector[A] Индексированный список
  //Легко получить элемент по индексу, добавить элемент в начало или конец
  // Set[A] Map[A,B]

  //val initial = Vector[String]("stepik")
  //val mid = "scala" +: "+" +: initial
  //val strings = mid :+ "=" :+ "love"
  //strings.mkString(" ")
  //println(strings)

  val list = List(2,5,7,1,4)
  list.sorted // метод сортировки
  for (i <- list.sorted)
    println(i)

  val random_list = List.fill(Random.nextInt(100))(Random.nextInt(1000)) // список с рандомным кол-вом элементов и рандомным значением
  def merge(as: List[Int], bs: List[Int], acc: List[Int] = Nil): List[Int] =
    as match {
      case List() => acc.reverse ++ bs
      case a +: restA => bs match {
        case List() => acc.reverse ++ as
        case b +: restB =>
          if (a < b) merge(restA, bs, a :: acc)
          else merge(as, restB, b :: acc)
      }
    }
  // Оператор +: добавляет элемент в начало коллекции, оператор :+ - в конец, оператор :: - в начало списка (только для коллекции типа List)
  merge(List(2,5,6), List(3,5,8));

  def mergeSort(as: List[Int]): List[Int] =
    as match {
      case Nil | (_ :: Nil) => as
      case _ =>
        val (left, right) = as.splitAt(as.length / 2)
        val leftSorted = mergeSort(left)
        val rightSorted = mergeSort(right)
        merge(leftSorted, rightSorted)
    }
  println(mergeSort(random_list)==random_list.sorted)

  def kOrder(list: List[Int], k: Int): Int = {
    list match {
    case a +: restA =>
      val left = restA.filter(_ <= a) // запихивает в список оставшихся столько, сколько меньше первого элемента
      left.length match {
        case len if len + 1 == k => a
        case len if len + 1 > k => kOrder(left, k)
        case len if len + 1 < k => kOrder(restA.filter(_ > a), k - len - 1)
      }
    case Nil => 0
  }
  }

  //val n: Int = readInt()
  val l = List(2,3,5,6,7)
  //println(kOrder(l, n))

  //val map = Map("Москва" -> 12e6, "Питер" -> 5e6)

  val cities = Vector("Москва", "Волгоград", "Питер")
  cities(1) // Волгоград
  cities.head // Москва
  cities.last //Питер
  cities.size // 3
  cities.contains("Москва") // true
  cities.indices //0 until 3
  /*val cityMap = Map("Москва" -> 12e6,
    "Питер" -> 5e6,
    "Волгоград" -> 1e6)
  cityMap("Питер") // 5000000.0
  cityMap.get("Москва") // Some(1.2E7)
  cityMap.get("Петушки") //None
  cityMap.size // 2
  cityMap.contains("Питер") // true
  cityMap.keySet // Set("Москва", "Питер")
  cityMap - "Питер"
  cityMap -- List("Москва", "Волгоград")
  val citySet = Set("Москва", "Волгоград", "Питер")
  citySet("Волгоград") // true
  citySet("Петушки") // false
  citySet.size // true
  citySet.contains("Петушки") // false
  citySet - "Питер" // true
  citySet -- List("Москва", "Волгоград")*/

  val nums = Vector.range(1, 11)
  nums.slice(3, 7)
  nums.tail // все кроме первого
  nums.init // все кроме последнего
  nums.take(3)
  nums.drop(3)
  nums.takeRight(3)
  nums.dropRight(3)

  val num = Vector.range(1, 21)
  val odds = num.filter(_ % 2 == 1) // вернет подхдящие
  val evens = num.filterNot(_ % 2 == 1) // вернет не подходящие
  val (odds1, evens1) = num.partition(_ % 2 == 1) // вернет пару подходящих/не подходящих
  val small = num.takeWhile(_ < 10) // возвращает с начала, пока подходит
  val big = num.dropWhile(_ < 10) // вернет все, кроме takeWhile
  val (small1, big1) = num.span(_ < 10) // вернет пару, образованную takeWhile dropWhile

  /*val nums = List.range(0, 10)
  val alpha = 'A' to 'Z'
  val nums2 = nums.map(i => alpha(i))
  val nums3 = nums.map(alpha)
  val charLists: List[Char] =
   nums.collect{
   case i if i % 2 == 0 => alpha(i / 2 * 3)
   case 3 => '_'
   case 5 | 7 => '!'
  }*/

  def isPrime(x : Long) : Boolean = {
    primes.takeWhile(p=>p * p <= x).forall(x % _ != 0)
  }
  println(isPrime(12))
  lazy val primes: Stream[Long] = 2L #:: Stream.iterate(3L)(_ + 2L).filter(isPrime)
  primes.take(50).force // взять 50 чисел(по условию чтобы были простые), force-принудительно вычислить их

  list.takeWhile(_ < 100).collect{case e if e % 4 == 0 => e / 4}.init.foreach(println)

  val points = List(1, 3)
  val chr1 = List('x', 'x', 'x', 'x', 'x')
  val chr2 = List('y', 'y', 'y', 'y', 'y')

  def cross(indexes: List[Int], list1: List[Char], list2: List[Char]): (List[Char], List[Char]) = {
    if (indexes.isEmpty)
      (list1, list2)
    else
      cross(indexes.tail,
        list1.patch(indexes.head, list2.drop(indexes.head), list1.length - indexes.head),
        list2.patch(indexes.head, list1.drop(indexes.head), list2.length - indexes.head))
  }

  val crossover = cross(points, chr1, chr2)
  println(crossover._1.mkString)
  println(crossover._2.mkString)
}
