package repository

import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.implicits._
import model.{ErrorMessage, Tweet}

import java.util.UUID

trait TweetRepository {
  def addTweet(tweet: Tweet): IO[String]

  def deleteTweetByID(tweetID: String): IO[Unit]

  def getTweetByID(tweetID: String): IO[Option[Tweet]]

  def getTweetsByUser(userID: String): IO[List[Tweet]]
}

class TweetRepositoryImpl(feed: Ref[IO, Map[String, Tweet]]) extends TweetRepository {
  def addTweet(tweet: Tweet): IO[String] = {
    val id = UUID.randomUUID().toString
    feed.update { myMap =>
        val newTweet = tweet.copy(id = id)
        myMap + (newTweet.id -> newTweet)
      }.as(id)
  }

  def deleteTweetByID(tweetID: String): IO[Unit] = {
      feed.update { myMap =>
        myMap - tweetID
      }
  }

  def getTweetByID(tweetID: String): IO[Option[Tweet]] = {
    for {
      unwrapFeed <- feed.get
      maybeTweet = unwrapFeed.get(tweetID)
    } yield maybeTweet
  }

  def getTweetsByUser(userID: String): IO[List[Tweet]] = {
    for {
      unwrapFeed <- feed.get
      tweets = unwrapFeed.values.filter(tweet => tweet.userID == userID).toList
    } yield tweets
  }
}

object TweetRepository {
  def make: IO[TweetRepositoryImpl] = {
    for {
      unwrapIO <- Ref.of[IO, Map[String, Tweet]](Map.empty)
    } yield new TweetRepositoryImpl(unwrapIO)
  }
}