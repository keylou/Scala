object Main extends App{
  def numberName(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case 3 | 4 => "three or four" // | == or
    case x if x % 2 == 0  => "unknown even"
    case _ => "unknown" // дефолт значение всегда в конце
  }

  case class Address(country: String, city: String)
  def addressInfo(address: Address): String = address match {
    case Address("Russia", "Moscow") => "russian capital"
    case Address("Russia", _) => "russian"
    case Address("Japan", "Tokio") => "japanese capital"
    case Address("Japan", _) => "japanese"
    case _ => "no info"
  }
  def addressInfo2(address: Address): String = address match {
    case Address("Russia", "Moscow") => "russian capital"
    case Address("Russia", city) => s"russian $city"
    case Address("Japan", "Tokio") => "japanese capital"
    case Address("Japan", city) => s"japanese $city"
    case _ => "no info"
  }
  def sum(xs: List[Int], start: Int = 0) = xs match {
    case List() => start
    case List(x) => start + x
    case List(x, y) => start + x + y
    case List(x, y, z) => start + x + y + z
    case _ => throw new Exception("too many elements")
  }
  val address = "\\w+, \\w+".r // регулярное выражение, которое соответствует - слово, слово

  def isAddress(string: String): Boolean = string match {
    case address() => true //можем проверять кейс совпедаения с регулярным выражением
    case _ => false
  }

  case class Pet(name: String, says: String)

  val pet = Pet("r2d2", "01meow10")

  val regex = "([\\w]*[0|1]+[\\w]*)".r

  val kind = pet match {
    case Pet(_, "meow") => "cat"
    case Pet(_, "nya") => "cat"
    case Pet("Rex", _) => "dog"
    case Pet(_, regex(_)) => "robot"
    case _ => "unknown"
  }
  println(kind)


  val input = List("oleg", "oleg@email.com", "7bdaf0a1be3", "a8af118b1a2", "28d74b7a3fe")
  val nameRegex = "([a-zA-Z]+)".r
  val mailRegex = "\\w+@(\\w+.\\w+)".r
  val contactRegex = "([a-zA-Z]+) \\w+@(\\w+.\\w+)".r

  val result = input match {
    case List(nameRegex(name), mailRegex(mail), rest@_*) => s"$name $mail"
    case List(contactRegex(name, mail), rest@_*) => s"$name $mail"
    case _ => "invalid"
  }
  println(result)
}
