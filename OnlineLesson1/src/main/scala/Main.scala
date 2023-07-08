object Main extends App {
  // 12345 -> "textA", 34521 -> textB, 54321 -> "textC"
  val Tweet1 = Tweet(12345, "textA", "Alex")
  val Tweet2 = Tweet(34521, "textB", "Bob")
  val Tweet3 = Tweet(54321, "textC", "Clara")
  val F = new UserStoreImpl
  F.save(Tweet1)
  F.save(Tweet2)
  F.save(Tweet3)
  println(F.feed)

  F.save(Tweet(12345, "textAAAAAAAAAAAA", "Alex"))
  println(F.feed)

  F.redact(Tweet1.id, "Tweet1 is redacted")
  println(F.feed)

  F.delete(Tweet2.id)
  println(F.feed)

  val FoundTweet1 = F.get(Tweet1.id)
  println(FoundTweet1)

  val FoundTweet2 = F.get(Tweet2.id)
  println(FoundTweet2)
}