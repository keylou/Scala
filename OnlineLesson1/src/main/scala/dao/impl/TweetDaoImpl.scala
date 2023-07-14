package dao.impl

import dto.Tweet
import dao.TweetDao

import scala.collection.mutable

class TweetDaoImpl extends TweetDao {
  private val feed = new mutable.HashMap[Int, Tweet]
  private var nextId : Int = 1

  def save(tweet: Tweet): Tweet = {
    if (feed.contains(tweet.id))
      redact(tweet.id, tweet.text).get
    else {
      println("Added")
      // Для работы с неизменяемыми полями
      val newTweet = tweet.copy(id = nextId)
      feed += tweet.id -> newTweet
      nextId += 1
      newTweet
    }
  }

  def delete(tweetId: Int): Unit = {
    if (feed.contains(tweetId)) {
      println("Deleted")
      feed -= tweetId
    }
    else
      println("Not deleted")
  }

  def redact(tweetId: Int, text: String): Option[Tweet] = {
    feed.get(tweetId) match {
      case Some(value) =>
        println("Redacted")
        val returnTweet = value.copy(text=text)
        feed += value.id -> returnTweet
        Option(returnTweet)
      case None =>
        println("Not redacted")
        None
    }
  }

  def get(tweetId: Int): Option[Tweet] = {
    if (feed.contains(tweetId)) {
      println("Gotten")
      feed.get(tweetId)
    }
    else {
      println("Not gotten")
      feed.get(tweetId)
    }
  }

  override def getFeed: mutable.HashMap[Int, Tweet] = feed
}