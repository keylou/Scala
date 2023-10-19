package model

import cats.effect.{ContextShift, IO}
import cats.implicits._
import org.http4s.HttpRoutes
import service.{TweetService, UserService}
import sttp.tapir.server.http4s._

class Routes(tweetService: TweetService, userService: UserService)(implicit c: ContextShift[IO]) {
  def right[A](param: A): Either[ErrorMessage, A] = Right(param)
  def left[A](param: String): Either[ErrorMessage, A] = Left(ErrorMessage(param))

  def addTweet: HttpRoutes[IO] = Endpoints.addTweet.toRoutes(
    tweet => {
      userService.getUserByID(tweet.userID).flatMap {
        case Some(_) =>
          tweetService.addTweet(tweet).map(id => right(AddTweetResponse(id)))
        case None => IO(left(s"User ${tweet.userID} not found"))
      }
    }
  )

  def deleteTweetByID: HttpRoutes[IO] = Endpoints.deleteTweetByID.toRoutes(
    tweetID => tweetService.deleteTweetByID(tweetID).as(right(()))
  )

  def getTweetByID: HttpRoutes[IO] = Endpoints.getTweetByID.toRoutes(
    tweetID => tweetService.getTweetByID(tweetID).map {
      case Some(value) => Right(value)
      case None => Left(ErrorMessage("Tweet not found."))
    }
  )

  def getTweetsByUser: HttpRoutes[IO] = Endpoints.getTweetsByUser.toRoutes(
    userID => tweetService.getTweetsByUser(userID).map {
      case TweetsList(list) if list.nonEmpty => Right(TweetsList(list))
      case _ => Left(ErrorMessage(s"User $userID has no tweets."))
    }
  )

  def addUser: HttpRoutes[IO] = Endpoints.addUser.toRoutes(
    user => userService.addUser(user).map {
      case "Invalid email" => Left(ErrorMessage("Invalid email."))
      case "Invalid phone" => Left(ErrorMessage("Invalid phone."))
      case value => Right(AddUserResponse(value))
    }
  )

  def deleteUserByID: HttpRoutes[IO] = Endpoints.deleteUserByID.toRoutes(
    userID => {
      userService.getUserByID(userID).flatMap {
        case Some(value) =>
          for {
            _ <- tweetService.deleteTweetsByUser(value)
            res <- userService.deleteUserByID(userID).as(right(()))
          } yield res
        case None => IO(left(s"User ${userID} not found"))
      }
    }
  )

  def getUserByID: HttpRoutes[IO] = Endpoints.getUserByID.toRoutes(
    userID => userService.getUserByID(userID).map {
      case Some(value) => Right(value)
      case None => left(s"User $userID not found")
    }
  )

  def allTweetRoutes: HttpRoutes[IO] = getTweetByID <+> getTweetsByUser <+> deleteTweetByID <+> addTweet

  def allUserRoutes: HttpRoutes[IO] = addUser <+> deleteUserByID <+> getUserByID

  def allRoutes: HttpRoutes[IO] = allTweetRoutes <+> allUserRoutes
}
