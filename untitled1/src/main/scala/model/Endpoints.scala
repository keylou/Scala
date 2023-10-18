package model

import io.circe.generic.auto._
import org.http4s.ParseFailure
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._
object Endpoints {
  // POST /tweets
  val addTweet: Endpoint[Tweet, ErrorMessage, Unit, Nothing] =
    endpoint
    .post
    .in("api" / "v1" / "tweets")
    .in(jsonBody[Tweet])
    .errorOut(jsonBody[ErrorMessage])

  // DELETE /tweets/id
  val deleteTweetByID: Endpoint[String, ErrorMessage, Unit, Nothing] =
    endpoint
    .delete
    .in("api" / "v1" / "tweets")
    .in(path[String]("tweetId"))
    .errorOut(jsonBody[ErrorMessage])

  // GET /tweets/id
  val getTweetByID: Endpoint[String, ErrorMessage, Tweet, Nothing] =
    endpoint
    .get
    .in("api" / "v1" / "tweets")
    .in(path[String]("tweetId"))
    .errorOut(jsonBody[ErrorMessage])
    .out(jsonBody[Tweet])

  // GET /tweets?user=...
  val getTweetsByUser: Endpoint[String, ErrorMessage, TweetsList, Nothing] =
    endpoint
    .get
    .in("api" / "v1" / "tweets")
    .in(query[String]("userID"))
    .errorOut(jsonBody[ErrorMessage])
    .out(jsonBody[TweetsList])

}
