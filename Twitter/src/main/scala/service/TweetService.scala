package service

import cats.effect.IO
import model.{ErrorMessage, Tweet, TweetsList}
import repository.{TweetRepository, TweetRepositoryImpl}

class TweetService(tweetRepository: TweetRepository) {
  def addTweet(tweet: Tweet): IO[String] =
    tweetRepository.addTweet(tweet)
  def deleteTweetByID(tweetID: String): IO[Unit] =
    tweetRepository.deleteTweetByID(tweetID)
  def getTweetByID(tweetID: String): IO[Option[Tweet]] =
    tweetRepository.getTweetByID(tweetID)
  def getTweetsByUser(userID: String): IO[TweetsList] = {
    tweetRepository.getTweetsByUser(userID).map(listOfTweets => TweetsList(listOfTweets))
  }
}
