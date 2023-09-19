object Main extends App {
  val p1 = new Pair(42, "String")
  val p2 = new Pair[Any, Any](42, "String")
  def getMiddle[T](a: Array[T]) = a(a.length / 2)
  println(getMiddle(Array("Mary", "had", "a", "little", "lamb")))

  val p3 = new PairComparable("Fred", "Brooks")
  println(p3.smaller)

  val tmp: Option[Animal] = Some(Dog())
  // Dog наследует Animal. Реализация Option через +A. + дает возможность делать иерархию.
  // Если внутренний тип Some наследник внут. типа Option, то Some наследник Option.
  Function1
}

  trait Animal
  case class Dog() extends Animal
  case class Cat() extends Animal
class PairReplace[T](val first: T, val second: T) {
  // замещающий тип должен быть супертипом для типа компонента пары
  def replaceFirst[R >: T](newFirst: R) = new Pair(newFirst, second)
}
class PairComparable[T <: Comparable[T]](val first: T, val second: T) {
  // можно добавить в определение класса верхнюю границу
  // иначе: ограничить допустимые типы в классе
  def smaller = if (first.compareTo(second) < 0) first else second
}
class Pair[T, S](val first: T, val second: S) // параметризованный класс

// ЭТО ВСЕ ГРАНИЦЫ ПРЕДСТАВЛЕНИЯ, ЭТИМ НЕ ПОЛЬЗОВАТЬСЯ (ВРОДЕ)