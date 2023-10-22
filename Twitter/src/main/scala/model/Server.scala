package model

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import repository.{SubscribeRepository, TweetRepository, UserRepository}
import service.{SubscribeService, TweetService, UserService}

object Server extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    for {
      tweetRepository <- TweetRepository.make
      userRepository <- UserRepository.make
      subscribeRepository <- SubscribeRepository.make
      tweetService = new TweetService(tweetRepository)
      userService = new UserService(userRepository)
      subscribeService = new SubscribeService(subscribeRepository)
      routes = new Routes(tweetService, userService, subscribeService)
      apis = routes.allRoutes.orNotFound
      _ <- BlazeServerBuilder[IO]
        .bindHttp(8080, "localhost")
        .withHttpApp(apis)
        .resource
        .use(_ => IO.never)
    } yield ExitCode.Success
  }
}
