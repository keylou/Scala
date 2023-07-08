object Main extends App {
  // 12345 -> "textA", 34521 -> textB, 54321 -> "textC"
  val Tweet1 = (12345, "textA")
  val Tweet2 = (34521, "textB")
  val Tweet3 = (54321, "textC")
  val F = new Interface with Realisation
  F.save(Tweet1._1,Tweet1._2)
  F.save(Tweet2._1,Tweet2._2)
  F.save(Tweet3._1,Tweet3._2)
  println(F.Feed)

  F.save(12345,Tweet2._2)
  println(F.Feed)

  F.redact(Tweet1._1, "Tweet1 is redacted")
  println(F.Feed)

  F.delete(Tweet2._1)
  println(F.Feed)

  val FoundTweet1 = F.get(Tweet1._1)
  println(FoundTweet1)

  val FoundTweet2 = F.get(Tweet2._1)
  println(FoundTweet2)
}