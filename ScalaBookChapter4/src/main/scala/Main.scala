

object Main extends App {
  // ХЕШ таблицы чисто
  val scores1 = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8) // неизменяемый
  val scores = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8) // изменяемый
  val scoresempty = new scala.collection.mutable.HashMap[String, Int] // пустой, надо указать типы
  val bobsScoreallways = scores("Bob") // передает значение. bobsScoreallways = 3
  val bobsScoreif1 = if (scores.contains("Bob")) scores("Bob") else 0
  val bobsScoreif2 = scores.getOrElse("Bob", 0) // аналогия if

  // Можно поставить значение по умолчанию
  //  val scores1 = scores.withDefaultValue(0)
  //  val zeldasScore1 = scores1.get("Zelda")
  // Вернет 0, потому что ключ "Zelda" отсутствует
  //  val scores2 = scores.withDefault(_.length)
  //  val zeldasScore2 = scores2.get("Zelda")
  // Вернет 5, применив функцию length к отсутствующему ключу

  scores("Bob") = 10
  // Изменит значение существующего ключа "Bob" (предполагается, что
  // scores – изменяемый ассоциативный массив)
  scores("Fred") = 7
  // Добавит новую пару ключ/значение в scores (предполагается, что
  // scores – изменяемый ассоциативный массив)
  scores += ("Bob" -> 10, "Fred" -> 7) // аналогично верхнему
  scores -= "Alice" // удаление

  // Изменить неизменяемый ассоциативный массив невозможно, зато можно создать новый ассоциативный массив, содержащий необходимые изменения:
  // val newScores = scores + ("Bob" -> 10, "Fred" -> 7)

  // for ((k, v) <- map) k - ключ v - значение
  // for (i <- map) i - пара
  // scores.keySet Множество, такое как Set("Bob", "Cindy", "Fred", "Alice")
  // for (v <- scores.values) println(v) Выведет 10 8 7 10(значения)
  // for ((k, v) <- map) yield (v, k) для создания инвертированного мапа

  // Чтобы создать неизменяемое дерево вместо хеш-таблицы, используйте (какая-то бесполезная хрень)
  // val scores = scala.collection.immutable.SortedMap("Alice" -> 10, "Fred" -> 7, "Bob" -> 3, "Cindy" -> 8)

  // КОРТЕЖИ
  val t = (1, 3.14, "Fred")
  val two = t._2 // Присвоит переменной second значение 3.14
  val (first, second, third) = t // Присвоит переменной first значение 1,second – 3.14, third – "Fred"
  val (first2, second2, _) = t // Вместо переменных, соответствующих ненужным компонентам, можно использовать _

  //  С помощью кортежей удобно возвращать из функций несколько значений. Например, метод partition возвращает
  //  пару строк, содержащих символы, которые соответствуют и не соответствуют условию:
  //  "New York".partition(_.isUpper) // Вернет пару ("NY", "ew ork")

  // Можно объединить значения в кортежи с помощью zip
  val symbols = Array("<", "-", ">")
  val counts = Array(2, 10, 2)
  val pairs = symbols.zip(counts) // создаст массив пар Array(("<", 2), ("-", 10), (">", 2))
  // Можно преобразовать в мап с помощью .toMap

  val purchases = Map("Laptop" -> 1000, "Mouse" -> 50, "Headset" -> 70)
  val sale = for ((u, v) <- purchases) yield (u, v * 0.9)
  println(sale)

//  val task2 = new scala.collection.mutable.HashMap[String, Int]
//  val in = new java.util.Scanner(new java.io.File("input.txt"))
//  while (in.hasNext()){
//    if (task2.keySet.find(x => (x == in.toString)) == 0){ // почему дают задание с файлом, если я не вдупляю, как с ним работать
//      task2 += (in.toString -> 1)
//    }
//    else {
//      val Score = task2(in.toString) + 1
//      task2 += (in.toString -> Score)
//    }
//    in.next()
//  }

  def minmax(values: Array[Int]):(Int,Int) = {
    var min = values(0)
    var max = values(0)
    for (i<-1 until values.length) {
        if (min > values(i))
          min = values(i)
        else if (max < values(i))
          max = values(i)
      }
    (min,max)
  }
  println (minmax(Array(1,2,3,4,5,6)))

  def lteqgt(values: Array[Int], v: Int):(Int,Int,Int) = {
    var min = 0
    var max = 0
    var eq = 0
    for (i <- 0 until values.length) {
      if (values(i) < v)
        min += 1
      else if (v < values(i))
        max += 1
      else if (v == values(i))
        eq +=1
    }
    (min, eq, max)
  }

  println(lteqgt(Array(1, 2, 3, 4, 5, 6),3))
}
