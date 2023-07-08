trait UserStore {
  def save(tweet: Tweet): Unit

  def delete(tweetid: Int): Unit

  def redact(tweetid: Int, text: String): Unit

  def get(tweetid: Int): Option[Tweet]
}

class UserStoreImpl extends UserStore {
  val feed = new scala.collection.mutable.HashMap[Int, Tweet]
  def save(tweet: Tweet): Unit = {
    if (feed.contains(tweet.id))
      redact(tweet.id, tweet.text)
    else {
      println("Added")
      feed += tweet.id -> tweet
    }
  }

  def delete(tweetid: Int): Unit = {
    if (feed.contains(tweetid)) {
      println("Deleted")
      feed -= tweetid
    }
    else
      println("Not deleted")
  }

  def redact(tweetid: Int, text: String): Unit = {
    if (feed.contains(tweetid)) {
      println("Redacted")
      feed(tweetid).text = text
    }
    else
      println("Not redacted")
  }

  def get(tweetid: Int): Option[Tweet] = {
    if (feed.get(tweetid) != None) {
      println("Gotten")
      feed.get(tweetid)
    }
    else {
      println("Not gotten")
      feed.get(tweetid)
    }
  }
}
case class Tweet(id: Int, var text: String, user: String)