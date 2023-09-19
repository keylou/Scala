package dao

import dto.Tweet

import scala.collection.mutable
import scala.concurrent.Future

trait TweetDao {
  def getFeed: mutable.HashMap[Int, Tweet]
  def save(tweet: Tweet): Future[Tweet]
  def delete(tweetId: Int): Unit
  def redact(tweetId: Int, text: String): Future[Option[Tweet]]
  def get(tweetId: Int): Future[Option[Tweet]]
  def cleanAll (): Unit
  def findMinLength(): Int
  def findMaxLength(): Int
  def findMaxLengthT(): Future[Option[Tweet]]
  def findLongerThan(length: Int): Future[mutable.HashMap[Int, Tweet]]
  def countAllLetters(): Unit
}