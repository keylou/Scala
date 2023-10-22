package service

import cats.effect.IO
import model.{ErrorMessage, Tweet, TweetsList, User}
import repository.SubscribeRepository

class SubscribeService(subscribeRepository: SubscribeRepository) {
  def getListOfSubscribes(ID: String): IO[List[String]] =
    subscribeRepository.getListOfSubscribes(ID)

  def unsubscribeFromUser(subscriber: String, blog: String): IO[Unit] =
    subscribeRepository.unsubscribeFromUser(subscriber, blog)

  def subscribeToUser(subscriber: String, blog: String): IO[Unit] =
    subscribeRepository.subscribeToUser(subscriber, blog)

  def deleteUserByID(ID: String): IO[Unit] =
    subscribeRepository.deleteUserByID(ID)

  def unsubscribeEveryoneFromUser(ID: String): IO[Unit] =
    unsubscribeEveryoneFromUser(ID)
}
