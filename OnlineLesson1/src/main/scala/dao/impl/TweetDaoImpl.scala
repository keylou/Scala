package dao.impl

import dto.Tweet
import dao.TweetDao

import scala.collection.mutable

class TweetDaoImpl extends TweetDao {
  private val feed = new mutable.HashMap[Int, Tweet]
  private var nextId : Int = 1

  def save(tweet: Tweet): Tweet = {
    if (feed.contains(tweet.id))
      redact(tweet.id, tweet.text)
    else {
      println("Added")
      tweet.id = nextId
      feed += tweet.id -> tweet
      nextId += 1
      tweet
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

  def redact(tweetId: Int, text: String): Tweet = {
    if (feed.contains(tweetId)) {
      println("Redacted")
      feed(tweetId).text = text
    }
    else
      println("Not redacted")

    feed(tweetId)
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