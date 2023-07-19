import dto.Tweet
import dao.TweetDao
import dao.impl.TweetDaoImpl

object Main extends App {
  // 1 -> "textA", 2 -> textB, 3 -> "textC"

  private val Tweet1 = Tweet("textAdfafasfsafa", "Alex")
  private val Tweet2 = Tweet("textBasffsa", "Bob")
  private val Tweet3 = Tweet("textC", "Clara")
  private val F:TweetDao = new TweetDaoImpl
  val tmpTweet1 = F.save(Tweet1)
  F.save(Tweet2)
  F.save(Tweet3)
  println(F.getFeed)

  F.save(Tweet("textAAAAAAAAAAAA", "Alex")) // 4
  println(F.getFeed)

  F.redact(Tweet1.id, "Tweet1 is redacted")
  println(F.getFeed)

  F.delete(Tweet2.id)
  println(F.getFeed)

  private val FoundTweet1 = F.get(Tweet1.id)
  println(FoundTweet1)

  private val FoundTweet2 = F.get(Tweet2.id)
  println(FoundTweet2)

  private val m = F.findLongerThan(2)
  println(m)

  F.countAllLetters()
}
