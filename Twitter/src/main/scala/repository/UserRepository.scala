package repository

import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.implicits._

import scala.util.matching.Regex
import model.{InvalidEmailError, InvalidPhoneError, User}

import java.util.UUID

trait UserRepository {
  def addUser(user: User): IO[String]

  def deleteUserByID(userID: String): IO[Unit]

  def getUserByID(userID: String): IO[Option[User]]
}

class UserRepositoryImpl(userDB: Ref[IO, Map[String, User]]) extends UserRepository {
  def addUser(user: User): IO[String] = {
    if (!("^(\\w+)@(\\w+).(\\w+)$".r matches user.email))
      IO.raiseError(InvalidEmailError(user.email))
    else if (!("^\\d{1} \\d{3} \\d{3} \\d{2} \\d{2}$".r matches user.phone))
      IO.raiseError(InvalidPhoneError(user.phone))
    else {
      val id = UUID.randomUUID().toString
      userDB.update { myMap =>
        val newUser = user.copy(id = id, phone = "+" + user.phone)
        myMap + (newUser.id -> newUser)
      }.as(id)
    }
  }

  def deleteUserByID(userID: String): IO[Unit] = {
    userDB.update { myMap =>
      myMap - userID
    }
  }

  def getUserByID(userID: String): IO[Option[User]] = {
    for {
      unwrapDB <- userDB.get
      maybeUser = unwrapDB.get(userID)
    } yield maybeUser
  }
}

object UserRepository {
  def make: IO[UserRepositoryImpl] = {
    for {
      unwrapIO <- Ref.of[IO, Map[String, User]](Map.empty)
    } yield new UserRepositoryImpl(unwrapIO)
  }
}