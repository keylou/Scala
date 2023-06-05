object Main extends App {
  // Option[A] - Означает, что в данном месте может быть
  // значение типа A, а может не быть ничего
  // Для этого у него есть ровно два подтипа
  // Some[A] - Контейнер, содержащий значение
  // None - Объект, означающий отсутствие значения
  // Нужно для решения проблем null reference
  // Всюду, где для обозначения возможного отсутствия
  // значения может использоваться пустая ссылка, в scala
  // используется Option[A]

  def divide(x: Int, y: Int): Option[Int] =
    if (y == 0) None else Some(x / y)

  def showDivide(x: Int, y: Int): String =
    divide(x, y) match {
      case Some(d) => s"$x = $d * $y"
      case None => "null division"
    }
  println(showDivide(1,1))

  // чтобы не использовать match
  println(divide(7, 1).getOrElse(1)) //либо получает значение из функции, либо если None, то то что в скобках GetOrElse
  divide(7, 0).orElse(divide(7, 2)) //если первое None, то выполняет второе
  println(divide(7, 0).map(x => x + 6)) //если есть значение, то преобразует, если нет, оставит пустым(None)
  println(divide(17, 3).flatMap(x => divide(x,3))) //то же самое, что и map, только закидывает в функцию
  println(divide(7, 4).filter(x => x > 2)) //если значение не соответствует, то оно становится пустым
  println(divide(17, 3).collect {
    case x if x > 4 => x + 4
  }) //сами пишем в случае чего чему преобразовывать

  def showDivide2(x: Int, y: Int): String =
    divide(x, y)
      .map(d => s"$x = $d * $y")
      .getOrElse("null division")

  val string = "scala + [stepik] = love"
  string.indexOf("[") //вернет индекс [, если ее нет, то вернет -1
  def indexOf(s: String, pattern: String): Option[Int] =
    Option(s.indexOf(pattern)).filter(_>=0)

  indexOf(string, "[") // 8
  indexOf(string, "[") // None

  def brackets(s: String): Option[(Int,Int)] =
    indexOf(string, "[").flatMap{opening =>
      indexOf(string, "]")
        .filter(_>opening)
      .map(closing => (opening, closing))
    }

  println(brackets(string))

  def cutBrackets(s: String): Option[String]=
    brackets(s).map{
      case(opening,closing) => s.substring(opening+1,closing)
    }
  println(cutBrackets(string))


}
