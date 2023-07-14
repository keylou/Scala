package dao

import dto.Tweet

import scala.collection.mutable

trait TweetDao {
  def getFeed: mutable.HashMap[Int, Tweet]
  def save(tweet: Tweet): Tweet

  def delete(tweetId: Int): Unit

  def redact(tweetId: Int, text: String): Option[Tweet]

  def get(tweetId: Int): Option[Tweet]
}