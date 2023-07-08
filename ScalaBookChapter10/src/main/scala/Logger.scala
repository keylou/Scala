// Трейты - интерфейсы
trait Logger {
  def log(msg: String) // Абстрактный метод
}
//не требуется объявлять метод абстрактным – нереализованные методы в трейтах автоматически становятся абстрактными
trait ConsoleLogger extends Logger { // extends, не implements
  def log(msg: String) { println(msg) } // override не нужно
}

// Если потребуется унаследовать более одного трейта,
// дополнительные трейты можно добавить вслед за первым через ключевое слово with
// class ConsoleLogger extends Logger with Cloneable with Serializable

//class SavingsAccount extends Account with ConsoleLogger {
//  def withdraw(amount: Double) {
//    if (amount > balance) log("Insufficient funds") Используется метод класса/трейта ConsoleLogger
//    else balance -= amount
//  }
//  ...
//}

// Пусть есть класс ... SavingsAccount extends Account with Logger
// При создании объектов, мы можем подмешать дочерние трейты Logger
// val acct = new SavingsAccount with ConsoleLogger
// тогда метод log будет вызван у ConsoleLogger
// val acct2 = new SavingsAccount with FileLogger
// тогда метод log будет вызван у FileLogger

// В класс или в объект можно добавить несколько трейтов, которые
// будут вызывать друг друга, начиная с последнего.
//trait TimestampLogger extends ConsoleLogger {
//  override def log(msg: String) {
//    super.log(s"${java.time.Instant.now()} $msg")
//  }
//}
//trait ShortLogger extends ConsoleLogger {
//  override def log(msg: String) {
//    super.log(
//      if (msg.length <= 15) msg else s"${msg.substring(0, 12)}..."
//    )
//  }
//}
// super.log вызывает следующий трейт в иерархии

trait TimestampLogger extends Logger {
  abstract override def log(msg: String) {
    super.log(s"${java.time.Instant.now()} $msg") // компилятор не знает, будет ли метод super реализован при вызове,
  }                                                     // поэтому необходимо abstract перед методом
}
trait ShortLogger extends Logger {
  val maxLength = 15 // Конкретное поле, так как присвоено начальное значение
  //  val maxLength: Int // Абстрактное поле и должно переопределяться в конкретном подклассе.
  abstract override def log(msg: String) {
    super.log(
      if (msg.length <= maxLength) msg
      else s"${msg.substring(0, maxLength - 3)}...")
  }
}
// Класс, в который будет подмешан этот трейт, получит поле maxLength
// НЕ НАСЛЕДУЕТ, А ПОЛУЧАЕТ

// Для абстрактного поля
// val acct = new SavingsAccount with ConsoleLogger with ShortLogger {val maxLength = 20}


// трейты могут иметь в своем теле конструкторы
trait FileLogger extends Logger {
  val out = new PrintWriter("app.log") // Часть конструктора трейта
  out.println(s"# ${java.time.Instant.now()}")
  // Так же часть конструктора
  def log(msg: String) { out.println(msg); out.flush() }
}
// Трейты не могут иметь конструкторов с параметрами
val acct = new SavingsAccount with FileLogger("myapp.log")
// Ошибка: Трейты не могут иметь конструкторов с параметрами
// нужно заменить абстрактным полем
trait FileLogger extends Logger {
  val filename: String
  val out = new PrintStream(filename)
  def log(msg: String) { out.println(msg); out.flush() }
} // Нужно писать вот так
val acct = new { // Блок опережающего определения после new
  val filename = "myapp.log"
} with SavingsAccount with FileLogger

// Трейты могут наследовать классы. Такие классы становятся суперклассами для любых классов,
// куда подмешиваются подобные трейты.
// Трейт имеет определение собственного типа

// trait LoggedException extends Logged {
// this: Exception =>
// def log() { log(getMessage()) }}
// Он не наследует Exception, но принимает его тип
// Трейт может подмешиваться только в подклассы класса Exception

// Структурный тип
// trait LoggedException extends Logged {
// this: { def getMessage() : String } =>
// def log() { log(getMessage()) }}
// Трейт можно подмешивать в любые классы, имеющие метод getMessage