abstract class Interface {
  val Feed = new scala.collection.mutable.HashMap[Int, String]

  def save(tweetid: Int, text: String): Unit

  def delete(tweetid: Int): Unit

  def redact(tweetid: Int, text: String): Unit

  def get(tweetid: Int): (Int, Option[String])
}

trait Realisation extends Interface {
  def save(tweetid: Int, text: String): Unit = {
    if (Feed.contains(tweetid))
      redact(tweetid, text)
    else {
      println("Added")
      Feed += tweetid -> text
    }
  }

  def delete(tweetid: Int): Unit = {
    if (Feed.contains(tweetid)) {
      println("Deleted")
      Feed -= tweetid
    }
    else
      println("Not deleted")
  }

  def redact(tweetid: Int, text: String): Unit = {
    if (Feed.contains(tweetid)) {
      println("Redacted")
      Feed(tweetid) = text
    }
    else
      println("Not redacted")
  }

  def get(tweetid: Int): (Int, Option[String]) = {
    if (Feed.get(tweetid) != None) {
      println("Gotten")
      (tweetid, Feed.get(tweetid))
    }
    else {
      println("Not gotten")
      (-1, None)
    }
  }
}