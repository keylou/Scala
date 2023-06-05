object Main extends App {
  // val y: (Int, Int) => Int =  ИМЯ (ТИП ПАРАМЕТРОВ) => ТИП РЕЗУЛЬТАТА
  val plus: (Int, Int) => Int = (x, y) => x + y
  val plus2 = (x: Int, y: Int) => x + y // если указывать тип параметров с именем, то Scala сама выведет тип функции
  val plus3 = (_: Int) + (_ : Int) // если параметры используются 1 раз и по порядку, как в списке

  def addOne(x: Int) = x + 1 // метод
  val add1 = addOne _ // эта-конверсия (_ после имени метода) - конвертирует в функцию

  def greaterOn(f: Int => Int): (Int, Int) => Boolean = // метод принимает функцию, функция принимает 2 значения, а возвращает bool
    (x, y) => f(x) > f(y)
  val greaterOnOnes = greaterOn(x => x % 10)
  greaterOnOnes(23, 45) // false
  greaterOnOnes(27, 45) // true

  def plus4: Int => Int => Int = x => y => x + y // каррирование
  plus4(1)(2) // 3

  val plus5 = (x: Int, y: Int, z: Int) => x + y + z
  val plus5c: Int => Int => Int => Int = plus5.curried
  plus5c(1)(2)(plus2(1,2)) // 6
  println(plus5c(1)(2)(plus2(1,5)))

  val plus1 = (_: Int) + 1
  val mul3 = (_: Int) * 3
  // Композиция
  val plusThenMul = plus1 andThen mul3// Берет результат функции слева и передают в функцию справа
  val plusBeforeMul = plus1 compose mul3// Берет результат функции справа и передают в функцию слева

  val toadd1 = (_: Int) + 1
  println(toadd1(2))

  val calc = (f: Int => Int) => f(42) // ?передает значение f в функцию, которая является параметром?
  println(calc(toadd1))
  println(calc(_+7))

  def SumTo (x:Int):Int = if(x == 0) 0 else x+ SumTo(x-1)
  println(calc(SumTo))
  // Нет SumTo, но нужна рекурсия, совсем непонятно, чувак чисто сам себе объясняет
  //println(calc((x:Int)=> if(x == 0) 0 else x+))

  val Mul3 = 3 * (_: Double)
  val pow2 = (x: Double) => x * x


  println((pow2.andThen[Double] _)(Mul3)(5))
}
