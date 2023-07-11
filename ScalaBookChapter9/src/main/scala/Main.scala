import java.io.PrintWriter
import scala.io.Source
object Main extends App{
  val source = Source.fromFile("input.txt", "UTF-8")
  // Определение кодировки можно опустить, если известно, что
  // кодировка файла совпадает с кодировкой по умолчанию в системе
  val lineIterator = source.getLines
  // Source.fromFile(...).getLines.toArray возвращает все строки из файла в массив/буфер;
  // Source.fromFile(...).mkString возвращает содержимое файла как строку;
  //for (l <- lineIterator)
   //println(l) // обработка строк
  //for (c <- source)
    //println(c) // обработка символов
  val iter = source.buffered // указывает на символ, при обработке не будет передвигать указатель в файле
  /*while (iter.hasNext) {
    if (iter.head is nice)
      обработать
    iter.next
    else
    ...
  }*/
  val tokens = source.mkString.split("\\s+") // разделение по пробелам
  val numbers = for (w <- tokens) yield w.toDouble
  val source1 = Source.fromURL("http://horstmann.com", "UTF-8")
  val source2 = Source.fromString("Hello, World!")
  // Читает из указанной строки – удобно для отладки
  val source3 = Source.stdin
  // Читает из стандартного ввода

  // Позор года: Scala не имеет встроенной поддержки записи в файлы
  // Нужно пользовать классом java.io.PrintWriter
  val out = new PrintWriter("numbers.txt")
  for (i <- 1 to 100) out.println(i)
  out.close()


  // ВАЖНО+СЛОЖНО: При обработке входных данных часто бывает необходимо использовать регулярные выражения
  val numPattern = "[0-9]+".r // .r = regex
  val wsnumwsPattern = """\s+[0-9]+\s+""".r // если есть слеши или кавычки, юзать """ """
  for (matchString <- numPattern.findAllIn("99 bottles, 98 bottles")) // findallin - все совпадения
    println(matchString)
  // val matches = numPattern.findAllIn("99 bottles, 98 bottles").toArray в массив
  val firstMatch = wsnumwsPattern.findFirstIn("99 bottles, 98 bottles") // findfirstin - результатом будет Option
  numPattern.replaceFirstIn("99 bottles, 98 bottles", "XX")
  // "XX bottles, 98 bottles"
  numPattern.replaceAllIn("99 bottles, 98 bottles", "XX")
  // "XX bottles, XX bottles"
  numPattern.replaceSomeIn("99 bottles, 98 bottles",
    m => if (m.matched.toInt % 2 == 0) Some("XX") else None)
  // "99 bottles, XX bottles"


  // Группы в регулярках
  val numitemPattern = "([0-9]+) ([a-z]+)".r
  for (m <- numitemPattern.findAllMatchIn("99 bottles, 98 bottles"))
    println(m.group(1)) // Выведет 99 и 98
    // println(m.group(2)) // Выведет bottles и bottles
  
}
