object Main extends App {
  val myCounter = new Counter // Или new Counter()
  myCounter.increment()
  println(myCounter.current)


  val chatter = new Network
  val myFace = new Network

  val fred = chatter.join("Fred")
  val wilma = chatter.join("Wilma")
  fred.contacts += wilma // OK
  val barney = myFace.join("Barney") // Имеет тип myFace.Member
  // fred.contacts += barney
  // Нет, нельзя добавить myFace.Member в буфер элементов chatter.Member


  val tinkoff = new BankAccount(1000)
  tinkoff.deposit(1123)
  println(tinkoff.info)


  val now = new Time (15,28)
  val hour = new Time (16,28)
  println(now.info)
  println(hour.info)
  println(now.before(hour))
}
