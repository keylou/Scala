package model

import cats.effect.{ContextShift, IO}
import cats.implicits._
import org.http4s.HttpRoutes
import service.TweetService
import sttp.tapir.server.http4s._
class Routes(tweetService: TweetService)(implicit c: ContextShift[IO]) {
  def addTweet: HttpRoutes[IO] = Endpoints.addTweet.toRoutes(
    tweet => tweetService.addTweet(tweet)
  )
  def deleteTweetByID: HttpRoutes[IO] = Endpoints.deleteTweetByID.toRoutes(
    tweetID => tweetService.deleteTweetByID(tweetID)
  )
  def getTweetByID: HttpRoutes[IO] = Endpoints.getTweetByID.toRoutes(
    tweetID => {tweetService.getTweetByID(tweetID).map {
      case Some(value) => Right(value)
      case None => Left(ErrorMessage("Tweet not found."))
    }}
  )
  def getTweetsByUser: HttpRoutes[IO] = Endpoints.getTweetsByUser.toRoutes(
    userID => {
      tweetService.getTweetsByUser(userID).map {
        case TweetsList(list) if list.nonEmpty => Right(TweetsList(list))
        case _ => Left(ErrorMessage(s"User $userID has no tweets."))
      }
    }
  )

  def allRoutes: HttpRoutes[IO] = getTweetByID <+> getTweetsByUser <+> deleteTweetByID <+> addTweet
}
