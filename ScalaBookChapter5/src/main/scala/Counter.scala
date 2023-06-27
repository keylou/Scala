class Counter {
  private var value = 0 // Поля должны инициализироваться
  private var name = ""
  def this(value: Int) { // Дополнительный конструктор
    this() // Вызов главного конструктора
    this.value = value
  }
  def this(name: String, value: Int) { // Другой дополнительный конструктор
    this(value) // Вызов предыдущего дополнительного конструктора
    this.name = name
  }
  def increment() {
    if (value ==  Int.MaxValue)
      value=0
    value += 1
  } // Методы по умолчанию общедоступные
  def current() = value
  def setvalue(v: Int) = this.value = v;
  def isLess(other : Counter) = value < other.value

  // Если хочу, чтобы поле вообще никак не поменяли, то использовать val.
  // Метод может иметь доступ к приватным полям во всех объектах своего класса.
  // доступ private[this] ограничивает доступ только для методов этого объекта.
  // У класса всегда существует главный конструктор.
  // Дополнительный (называется this) должен начинаться с вызова главного конструктора или конструктора, объявленного выше.
  // Класс, не определяющий главный конструктор явно, получает главный конструктор без аргументов.
}