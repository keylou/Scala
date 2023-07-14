object Main extends App {
  // названием переменных может быть бувально все, так же функции можно сохранять внутри переменных
  val √ = scala.math.sqrt _

  // функциям необязательно иметь имена
  val triple = (x: Double) => 3 * x
  // аналогично def triple(x: Double) = 3 * x
  Array(3.14, 1.42, 2.0).map((x: Double) => 3 * x)
  // Array(9.42, 4.26, 6.0)

  // параметром функции может выступать другая функция
  def valueAtOneQuarter(f: (Double) => Double) = f(0.25)

  // Функции могут возвращать функции
  def mulBy(factor : Double) = (x : Double) => factor * x
  // Вызов mulBy(3), например, вернет функцию (x : Double) => 3 * x

  val quintuple = mulBy(5)
  quintuple(20) // 100

  // map применяет функцию ко всем элементам коллекции и возвращает коллекцию результатов.
  (1 to 9).map(0.1 * _) // 0.1 0.2 0.3 ...

  // foreach действует подобно map, но не возвращает значение
  (1 to 9).map("*" * _).foreach(println _) // Треугольник

  // filter возвращает все элементы, соответствующие определенному условию.
  (1 to 9).filter(_ % 2 == 0) // 2, 4, 6, 8

  // reduceLeft принимает функцию с двумя аргументами и применяет ее
  // ко всем элементам последовательности, двигаясь в направлении слева направо.
  (1 to 9).reduceLeft(_ * _) // 1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9

  // Двухместная функция сортировки
  "Mary had a little lamb".split(" ").sortWith(_.length < _.length) //Array("a", "had", "Mary", "lamb", "little").

  // Карринг - превращение функции с двумя аргументами в функцию с одним
  def mul(x: Int, y: Int) = x * y
  def mulOneAtATime(x: Int) = (y: Int) => x * y
  def mulOneAtATime(x: Int)(y: Int) = x * y


}
