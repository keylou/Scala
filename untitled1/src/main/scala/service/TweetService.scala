package service

import cats.effect.IO
import model.{ErrorMessage, Tweet, TweetsList}
import repository.TweetRepository

class TweetService(tweetRepository: TweetRepository) {
  def addTweet(tweet: Tweet): IO[Either[ErrorMessage, Unit]] =
    tweetRepository.addTweet(tweet)
  def deleteTweetByID(tweetID: String): IO[Either[ErrorMessage, Unit]] =
    tweetRepository.deleteTweetByID(tweetID)
  def getTweetByID(tweetID: String): IO[Option[Tweet]] =
    tweetRepository.getTweetByID(tweetID)
  def getTweetsByUser(userID: String): IO[TweetsList] = {
    tweetRepository.getTweetsByUser(userID).map(listOfTweets => TweetsList(listOfTweets))
  }
}
