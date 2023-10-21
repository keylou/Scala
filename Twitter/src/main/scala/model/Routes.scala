package model

import cats.effect.{ContextShift, IO}
import cats.implicits._
import org.http4s.HttpRoutes
import service.{SubscribeService, TweetService, UserService}
import sttp.tapir.server.http4s._

class Routes(tweetService: TweetService, userService: UserService, subscribeService: SubscribeService)(implicit c: ContextShift[IO]) {
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
    user => userService.addUser(user).attempt.map {
      case Left(value) => Left(ErrorMessage(value.getMessage))
      case Right(value) =>
        for {
          _ <- subscribeService.addUserToSubscribeDB(user.id)
        } yield ()
        Right(AddUserResponse(value))
    }
  )

  def deleteUserByID: HttpRoutes[IO] = Endpoints.deleteUserByID.toRoutes(
    userID => userService.getUserByID(userID).flatMap {
      case Some(value) =>
        List(tweetService.deleteTweetsByUser(value), userService.deleteUserByID(userID)).parSequence.as(right(()))
      case None => IO(left(s"User $userID not found"))
    }
  )

  def getUserByID: HttpRoutes[IO] = Endpoints.getUserByID.toRoutes(
    userID => userService.getUserByID(userID).map {
      case Some(value) => Right(value)
      case None => left(s"User $userID not found")
    }
  )

  def subscribeToUser: HttpRoutes[IO] = Endpoints.subscribeToUser.toRoutes(
    pairOfIDs => userService.getUserByID(pairOfIDs._1).flatMap {
      case Some(_) => userService.getUserByID(pairOfIDs._2.ID).flatMap {
        case Some(_) => subscribeService.subscribeToUser(pairOfIDs._1, pairOfIDs._2.ID).as(right(()))
        case None => IO(left(s"User ${pairOfIDs._2.ID} not found"))
      }
      case None => IO(left(s"User ${pairOfIDs._1} not found"))
    }
  )

  def unsubscribeFromUser: HttpRoutes[IO] = Endpoints.unsubscribeFromUser.toRoutes(
    pairOfIDs => userService.getUserByID(pairOfIDs._1).flatMap {
      case Some(_) => userService.getUserByID(pairOfIDs._2.ID).flatMap {
        case Some(_) => subscribeService.unsubscribeFromUser(pairOfIDs._1, pairOfIDs._2.ID).as(right(()))
        case None => IO(left(s"User ${pairOfIDs._2.ID} not found"))
      }
      case None => IO(left(s"User ${pairOfIDs._1} not found"))
    }
  )

  def getAllTweetsFromSubscribes: HttpRoutes[IO] = Endpoints.getAllTweetsFromSubscribes.toRoutes(
    userID => userService.getUserByID(userID).flatMap {
      case Some(_) =>
        for {
          listOfSubscribes <- subscribeService.getListOfSubscribes(userID)
          ioTweetsList = listOfSubscribes.foldLeft(IO[TweetsList] {
            TweetsList(List.empty)
          }) { (ac, ID) =>
            for {
              tl1 <- ac
              tl2 <- tweetService.getTweetsByUser(ID)
              res = TweetsList(tl1.list ::: tl2.list)
            } yield res
          }
          tweetsList <- ioTweetsList
        } yield Right(tweetsList)
      case None => IO(left(s"User $userID not found"))
    }
  )

  def allTweetRoutes: HttpRoutes[IO] = getTweetByID <+> getTweetsByUser <+> deleteTweetByID <+> addTweet

  def allUserRoutes: HttpRoutes[IO] = addUser <+> deleteUserByID <+> getUserByID

  def allSubscribeRoutes: HttpRoutes[IO] = subscribeToUser <+> unsubscribeFromUser <+> getAllTweetsFromSubscribes

  def allRoutes: HttpRoutes[IO] = allTweetRoutes <+> allUserRoutes <+> allSubscribeRoutes
}
