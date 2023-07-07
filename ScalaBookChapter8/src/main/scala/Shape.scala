import java.lang.Math
abstract class Shape {
  def S: Double
}
class Circle(r: Int) extends Shape {
  def S: Double = {
    Math.PI*r*r
  }
}
class Rectangle(a: Int,b: Int) extends Shape {
  def S: Double = {
    a*b
  }
}