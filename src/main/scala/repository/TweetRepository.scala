package repository

import cats.effect.IO
import cats.effect.concurrent.Ref
import model.{ErrorMessage, Tweet}

trait TweetRepository {
  def addTweet(tweet: Tweet): IO[Either[ErrorMessage, Unit]]
  def deleteTweetByID(tweetID: String): IO[Either[ErrorMessage, Unit]]
  def getTweetByID(tweetID: String): IO[Option[Tweet]]
  def getTweetsByUser(userID: String): IO[List[Tweet]]
}
class TweetRepositoryImpl extends TweetRepository {
  private val feed = Ref.of[IO, Map[Int, Tweet]](Map[Int, Tweet]((0-> Tweet(0,"sos","sas"))))
  def addTweet(tweet: Tweet): IO[Either[ErrorMessage, Unit]] = {
    for {
      unwrapFeedIO <- feed
      feedsMap <- unwrapFeedIO.get
      newTweet = tweet.copy(id = feedsMap.size)
      newMap = feedsMap + (newTweet.id -> newTweet)
      _ = unwrapFeedIO.set(newMap)
    } yield newTweet match {
      case Tweet(_,_,userID) if userID.isEmpty =>
        deleteTweetByID(newTweet.id.toString)
        Left(ErrorMessage("User ID is empty"))
      case _ => Right()
    }
  }
  def deleteTweetByID(tweetID: String): IO[Either[ErrorMessage, Unit]] = {
    for {
      unwrapFeedIO <- feed
      feedsMap <- unwrapFeedIO.get
      newMap = feedsMap.filterKeys(_ != tweetID.toInt).toMap
      maybeTweet = feedsMap.get(tweetID.toInt)
      _ = unwrapFeedIO.set(newMap)
    } yield maybeTweet match {
      case Some(value) => Right()
      case None => Left(ErrorMessage("Wrong ID"))
    }
  }
  def getTweetByID(tweetID: String): IO[Option[Tweet]] = {
    for {
      unwrapFeedIO <- feed
      feedsMap <- unwrapFeedIO.get
      maybeTweet = feedsMap.get(tweetID.toInt)
    } yield maybeTweet
  }
  def getTweetsByUser(userID: String): IO[List[Tweet]] = {
    for {
      unwrapFeedIO <- feed
      feedsMap <- unwrapFeedIO.get
      tweets = feedsMap.values.filter(tweet => tweet.userID == userID).toList
    } yield tweets
  }
}
