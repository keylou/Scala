package model

import io.circe.generic.auto._
import sttp.tapir._
import sttp.tapir.json.circe._

object Endpoints {
  // POST /tweets
  val addTweet: Endpoint[Tweet, ErrorMessage, AddTweetResponse, Nothing] =
    endpoint
      .post
      .in("api" / "v1" / "tweets")
      .in(jsonBody[Tweet])
      .errorOut(jsonBody[ErrorMessage])
      .out(jsonBody[AddTweetResponse])

  // DELETE /tweets/id
  val deleteTweetByID: Endpoint[String, ErrorMessage, Unit, Nothing] =
    endpoint
      .delete
      .in("api" / "v1" / "tweets")
      .in(path[String]("tweetID"))
      .errorOut(jsonBody[ErrorMessage])

  // GET /tweets/id
  val getTweetByID: Endpoint[String, ErrorMessage, Tweet, Nothing] =
    endpoint
      .get
      .in("api" / "v1" / "tweets")
      .in(path[String]("tweetID"))
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

  // POST /users
  val addUser: Endpoint[User, ErrorMessage, AddUserResponse, Nothing] =
    endpoint
      .post
      .in("api" / "v1" / "users")
      .in(jsonBody[User])
      .errorOut(jsonBody[ErrorMessage])
      .out(jsonBody[AddUserResponse])

  // DELETE /users/userID
  val deleteUserByID: Endpoint[String, ErrorMessage, Unit, Nothing] =
    endpoint
      .delete
      .in("api" / "v1" / "users")
      .in(path[String]("userID"))
      .errorOut(jsonBody[ErrorMessage])

  // GET /users/userID
  val getUserByID: Endpoint[String, ErrorMessage, User, Nothing] =
    endpoint
      .get
      .in("api" / "v1" / "users")
      .in(path[String]("userID"))
      .errorOut(jsonBody[ErrorMessage])
      .out(jsonBody[User])

  // PATCH /subscribes/userID    For one is subscribing
  val subscribeToUser: Endpoint[(String, UserID), ErrorMessage, Unit, Nothing] =
    endpoint
      .patch
      .in("api" / "v1" / "subscribes")
      .in(path[String]("userID"))
      .in(jsonBody[UserID])
      .errorOut(jsonBody[ErrorMessage])

  // PATCH /subscribes/userID    For one is unsubscribing
  val unsubscribeFromUser: Endpoint[(String, UserID), ErrorMessage, Unit, Nothing] =
    endpoint
      .patch
      .in("api" / "v1" / "subscribes")
      .in(path[String]("userID"))
      .in(jsonBody[UserID])
      .errorOut(jsonBody[ErrorMessage])

  // GET /subscribes/userID
  val getAllTweetsFromSubscribes: Endpoint[String, ErrorMessage, TweetsList, Nothing] =
    endpoint
      .get
      .in("api" / "v1" / "subscribes") // Должны ли эндпоинты из одной ветки иметь одинаковый путь?
      .in(path[String]("userID"))
      .errorOut(jsonBody[ErrorMessage])
      .out(jsonBody[TweetsList])
}
