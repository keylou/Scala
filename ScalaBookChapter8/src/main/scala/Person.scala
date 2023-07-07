// Наследование классов выполняется с помощью ключевого слова extends
// class Employee extends Person

// Для переопределения метода нужно использовать модификатор override

// Вызов метода суперкласса выполняется  с помощью ключевого слова super
// override def toString = s"${super.toString}[salary=$salary]"

// Для проверки принадлежности объекта к заданному классу используется метод isInstanceOf
// asInstanceOf - преобразует ссылку в ссылку на подкласс
// if (p.isInstanceOf[Employee]) val s = p.asInstanceOf[Employee] // s имеет тип Employee

// Чтобы убедиться, что p ссылается на объект именно класса Employee, а не одного из его подклассов, следует использовать проверку
// if (p.getClass == classOf[Employee])

class Person(val name: String) {
  override def toString = s"${getClass.getName}[name=$name]"
}
class SecretAgent(codename: String) extends Person(codename) {
  override val name = "secret" // Скрыть имя ...
  override val toString = "secret" // ... и имя класса
}

abstract class Person2 { // Абстрактный класс
  def id: Int // Каждый имеет идентификационный номер,
    // который вычисляется некоторым способом
}
class Student(override val id: Int) extends Person2
// Идентификационный номер студента просто передается конструктору

val alien = new Person("Fred") { // анонимный класс
  def greeting = "Greetings, Earthling! My name is Fred."
}

// Получившийся тип обозначается как Person{def greeting: String}.
// Этот тип можно использовать в качестве типа параметра:
def meet(p: Person{def greeting: String}) {
  println(s"${p.name} says: ${p.greeting}")
}

// В абстрактном классе могут быть абстрактные методы и поля.
// abstract class Person {
// val id: Int
// Нет начального значения - абстрактное поле с абстрактным методом чтения
// var name: String
// // Другое абстрактное поле с абстрактными методами чтения/записи
//}

//class Employee(val id: Int) extends Person { // Подкласс с конкретным свойством id
// var name = "" // и конкретным свойством name
//}

// Абстрактное поле всегда можно определять без указания типа:
// val fred = new Person {
// val id = 1729
// var name = "Fred"
//}

class Creature {
  val range: Int = 10
  val env: Array[Int] = new Array[Int](range)
}
class Ant extends Creature {
  override val range = 2
}
// Не сработает, так как конструктор Creature вызовется раньше, в итоге у муравья массив будет из 0 элементов
// чтобы исправить заключаем изменяемое значение в extends, а класс родитель в конце после with:
class Bug extends {
  override val range = 2
} with Creature