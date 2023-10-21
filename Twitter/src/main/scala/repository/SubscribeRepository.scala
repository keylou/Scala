package repository

import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.implicits._
import model.{Tweet, User}

import java.util.UUID

trait SubscribeRepository {
  def getListOfSubscribes(ID: String): IO[List[String]]

  def unsubscribeFromUser(subscriber: String, blog: String): IO[Unit]

  def subscribeToUser(subscriber: String, blog: String): IO[Unit]

  def addUserToSubscribeDB(ID: String): IO[Unit]
}

class SubscribeRepositoryImpl(subscribeDB: Ref[IO, Map[String, List[String]]]) extends SubscribeRepository {

  def getListOfSubscribes(ID: String): IO[List[String]] = {
    for {
      unwrapDB <- subscribeDB.get
      list = unwrapDB.get(ID).toList.flatten
    } yield list
  }
  def unsubscribeFromUser(subscriber: String, blog: String): IO[Unit] = {
    subscribeDB.update(myMap =>
      myMap.map { case (subscriber, list) =>
        (subscriber, list diff List(blog))
      }
    )
  }
  def subscribeToUser(subscriber: String, blog: String): IO[Unit] = {
    subscribeDB.update(myMap =>
      myMap.map { case (subscriber, list) =>
        (subscriber, list :+ blog)
      }
    )
  }

  def addUserToSubscribeDB(ID: String): IO[Unit] = {
    subscribeDB.update { myMap =>
      myMap + (ID -> List.empty)
    }
  }

}

object SubscribeRepository {
  def make: IO[SubscribeRepositoryImpl] = {
    for {
      unwrapIO <- Ref.of[IO, Map[String, List[String]]](Map.empty)
    } yield new SubscribeRepositoryImpl(unwrapIO)
  }
}