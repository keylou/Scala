import scala.collection.IterableOnce.iterableOnceExtensionMethods

object Main extends App {
  // filter map reduce fold flatMap
  val str1 = "kafllfasff"
  val str2 = str1.filter(a => a.toInt > 105)
  println(str2)
  val str3 = str1.map(a => a * 2)
  println(str3)
  val str4 = str1.map(c => c.toInt).toList
  println(str4)
  val str5 = str1.foldLeft(0)((accum, c) => accum + c)
  println(str5)


  val map1 = Map(1 -> List(1, 2, 3, 123, 14512), 2 -> List(4, 5, 6), 3 -> List(7, 8, 9, 0))
  val map2 = map1.filter(_._2.size > 3)
  println(map2)
  val map3 = map1.map {
    case (u, v) => (v.size * u, v.reverse)
  }
  println(map3)
  val map4 = map1.foldLeft(0)((accum2, e) => accum2 + e._2.sum)
  println(map4)
  val map5 = map1.flatMap(pair => pair._2.map(e => pair._1.toString + " -> " + e.toString)).toList.mkString(",") // mkstring   collection to string
  println(map5)
  val map6 = map1.flatMap(pair => pair._2.map(e => pair._1.toString + " -> " + e.toString))
    .toList
    .zipWithIndex
  val tmp = map6.foldLeft("")((accum, elem) => if (elem._2 != map6.length-1) {
    accum + elem._1 + ", "
  }
  else
    accum + elem._1
  )
  //val result = map6.substring(0, map6.length-2)
  println(tmp)
}
