// bad version without tapir library, IO.ref, etc.

import cats._
import cats.effect._
import cats.implicits._
import org.http4s.circe._
import org.http4s._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.dsl._
import org.http4s.dsl.impl._
import org.http4s.headers._
import org.http4s.implicits._
import org.http4s.server._
import org.http4s.server.blaze.BlazeServerBuilder

import java.time.Year
import java.util.UUID
import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.util.Try
object Main extends IOApp {
  case class Tweet(id: Int, text: String, user: String)

  val myTweet = Tweet(0,"There was somebody", "Not Leo")

  val feed: mutable.Map[Int, Tweet] = mutable.Map[Int,Tweet](myTweet.id -> myTweet)
  private def findTweetById(tweetId: String): Option[Tweet] =
    feed.get(tweetId.toInt)
  private def findTweetsByUser(user: String): List[Tweet] =
    feed.values.filter(_.user == user).toList
  private def deleteTweet(id: String) =
    feed.remove(id.toInt)
  private def addTweet(tweet: Tweet) =
    feed.put(feed.size, tweet)

  // GET all tweets by user
  // GET tweet by ID
  // DELETE tweet by ID
  // DELETE all tweets by user
  // POST tweet ?
  // PATCH tweet by id ?
  object UserQueryParamMatcher extends QueryParamDecoderMatcher[String]("user")
  object IDQueryParamMatcher extends QueryParamDecoderMatcher[String]("id")
  def tweetRoutes[F[_] : Monad]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "tweets" / id =>
        findTweetById(id) match {
          case Some(tweet) => Ok(tweet.asJson)
          case None =>  NotFound(s"No tweets with ID $id found in the data base")
        }

      case GET -> Root / "tweets" :? UserQueryParamMatcher(user) =>
        findTweetsByUser(user).length match {
          case 0 => NotFound(s"No tweets by user $user found in the data base")
         case _ => Ok(findTweetsByUser(user).asJson)
        }

      case DELETE -> Root / "tweets" / id =>
        findTweetById(id) match {
          case Some(tweet) =>
            deleteTweet(id)
            Ok()
          case None => NotFound(s"No tweets with ID $id found in the data base")
        }

      case DELETE -> Root / "tweets" :? UserQueryParamMatcher(user) =>
        findTweetsByUser(user).length match {
          case 0 => NotFound(s"No tweets by user $user found in the data base")
          case _ =>
            findTweetsByUser(user).foreach(t => deleteTweet(t.id.toString))
            Ok()
        }

      case POST -> Root / "tweets" => ???

      case PATCH -> Root / "tweets" / id => ???
    }
  }
  def tweetRoutesComplete[F[_] : Monad]: HttpApp[F] =
    tweetRoutes[F].orNotFound
  def run(args: List[String]): IO[ExitCode] = {
    val apis = Router(
      "/api" -> tweetRoutes[IO],
    ).orNotFound

    BlazeServerBuilder[IO](ExecutionContext.global)
      .bindHttp(8080, "localhost")
      .withHttpApp(apis)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
