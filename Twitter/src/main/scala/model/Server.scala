package model

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import repository.TweetRepository
import service.TweetService

object Server extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    for {
      tweetRepository <- TweetRepository.make
      tweetService = new TweetService(tweetRepository)
      routes = new Routes(tweetService)
      apis = routes.allRoutes.orNotFound
      _ <- BlazeServerBuilder[IO]
        .bindHttp(8080, "localhost")
        .withHttpApp(apis)
        .resource
        .use(_ => IO.never)
    } yield ExitCode.Success
  }
}
