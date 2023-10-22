package repository

import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.implicits._
import model.{Tweet, User}

import java.util.UUID

trait SubscribeRepository {
  def unsubscribeEveryoneFromUser(ID: String): IO[Unit]
  def deleteUserByID(ID: String): IO[Unit]

  def getListOfSubscribes(ID: String): IO[List[String]]

  def unsubscribeFromUser(subscriber: String, blog: String): IO[Unit]

  def subscribeToUser(subscriber: String, blog: String): IO[Unit]

}

class SubscribeRepositoryImpl(subscribeDB: Ref[IO, Map[String, List[String]]]) extends SubscribeRepository {
  def unsubscribeEveryoneFromUser(ID: String): IO[Unit] = {
    subscribeDB.update {  myMap =>
      myMap.map {
        case (sub,listOfSubscribes) => (sub,listOfSubscribes diff List(ID))
      }
    }
  }

  def deleteUserByID(ID: String): IO[Unit] = {
    subscribeDB.update { myMap =>
      myMap - ID
    }
  }

  def getListOfSubscribes(ID: String): IO[List[String]] = {
    for {
      unwrapDB <- subscribeDB.get
      list = unwrapDB.get(ID).toList.flatten
    } yield list
  }

  def unsubscribeFromUser(subscriber: String, blog: String): IO[Unit] = {
    subscribeDB.update { myMap =>
      val listOfSubscribersWithoutDeleted = myMap.get(subscriber).toList.flatten diff List(blog)
      myMap + (subscriber -> listOfSubscribersWithoutDeleted)
    }
  }

  def subscribeToUser(subscriber: String, blog: String): IO[Unit] = {
    subscribeDB.update { myMap =>
      val listOfSubscribersWithNew = myMap.get(subscriber).toList.flatten :+ blog
      myMap + (subscriber -> listOfSubscribersWithNew)
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