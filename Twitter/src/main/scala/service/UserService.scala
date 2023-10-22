package service

import cats.effect.IO
import model.User
import repository.{SubscribeRepository, UserRepository}

class UserService(userRepository: UserRepository) {
  def addUser(user: User): IO[String] =
    userRepository.addUser(user)
  def deleteUserByID(userID: String): IO[Unit] =
    userRepository.deleteUserByID(userID)
  def getUserByID(userID: String): IO[Option[User]] =
    userRepository.getUserByID(userID)
}