package dao.impl

import dto.Tweet
import dao.TweetDao

import scala.:+
import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.collection.mutable
import scala.collection.immutable

class TweetDaoImpl extends TweetDao {
  private var feed = new mutable.HashMap[Int, Tweet]
  private var nextId: Int = 1

  def save(tweet: Tweet): Tweet = {
    if (feed.contains(tweet.id))
      redact(tweet.id, tweet.text).get
    else {
      println("Added")
      // Для работы с неизменяемыми полями
      val newTweet = tweet.copy(id = nextId)
      feed += newTweet.id -> newTweet
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
        val returnTweet = value.copy(text = text)
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

  def cleanAll(): Unit = {
    feed = feed.map { case (u, v) =>
      val returnTweet = v.copy(text = "")
      (u, returnTweet)
    }
  }

  def findMaxLength(): Int = {
    val accum = 0

    val result = feed.foldLeft(accum)((x, y) => {
      x.max(y._2.text.length)
    })

    result
  }

  def findMinLength(): Int = {
    val accum = Int.MaxValue

    val result = feed.foldLeft(accum)((x, y) => {
      x.min(y._2.text.length)
    })

    result
  }

  def findMaxLengthT(): Option[Tweet] = {
    if (feed.isEmpty)
      None
    else {
      val result = feed.values.toList.reduce((x, y) => {
        if (x.text.length > y.text.length)
          x
        else
          y
      })
      Option(result)
    }
    // ЛУЧШЕ заменить reduce на foldleft
  }

  def findLongerThan(length: Int): mutable.HashMap[Int, Tweet] = {
    val result = feed.filter { case (u, v) => v.text.length > length }
    result
  }

  def countAllLetters(): Unit = {
    // map ===> из типа в другой тип
    // flatmap ===> из типа в коллекцию другого типа

    val freq2 = feed
      .values
      .toList
      .flatMap(tweet => tweet.text.chars().toArray.map(_.toChar.toLower))
      .foldLeft(Map[Char, Int]())((accum, c) => accum + (c -> (accum.getOrElse(c, 0) + 1)))
      .toList
      .sortBy(_._1)
      .sortWith((a,b) => a._2%2==0)

    println(freq2)
  }
}