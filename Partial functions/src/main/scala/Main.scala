object Main extends App {
  val divide10: PartialFunction[Int, Int] = {
    case 1 => 10
    case 2 => 5
    case 5 => 2
    case 10 => 1
  }
  divide10.isDefinedAt(2) == true
  divide10.isDefinedAt(3) == false
  divide10(2) == 5
  List.range(1, 11).collect(divide10)
  //divide10(3) == throw new MatchError

  val log: PartialFunction[Double, Double] = {
    case x if x>0 => math.log(x)
  }
}
