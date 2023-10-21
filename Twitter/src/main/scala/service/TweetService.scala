package service

import cats.effect.IO
import model.{Tweet, TweetsList, User}
import repository.TweetRepository

class TweetService(tweetRepository: TweetRepository) {
  def deleteTweetsByUser(user: User): IO[Unit] =
    tweetRepository.deleteTweetsByUser(user)
  def addTweet(tweet: Tweet): IO[String] =
    tweetRepository.addTweet(tweet)
  def deleteTweetByID(tweetID: String): IO[Unit] =
    tweetRepository.deleteTweetByID(tweetID)
  def getTweetByID(tweetID: String): IO[Option[Tweet]] =
    tweetRepository.getTweetByID(tweetID)
  def getTweetsByUser(userName: String): IO[TweetsList] = {
    tweetRepository.getTweetsByUser(userName).map(listOfTweets => TweetsList(listOfTweets))
  }
}
