package model

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import repository.{TweetRepository, UserRepository}
import service.{TweetService, UserService}

object Server extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    for {
      tweetRepository <- TweetRepository.make
      userRepository <- UserRepository.make
      tweetService = new TweetService(tweetRepository)
      userService = new UserService(userRepository)
      routes = new Routes(tweetService, userService)
      apis = routes.allRoutes.orNotFound
      _ <- BlazeServerBuilder[IO]
        .bindHttp(8080, "localhost")
        .withHttpApp(apis)
        .resource
        .use(_ => IO.never)
    } yield ExitCode.Success
  }
}
