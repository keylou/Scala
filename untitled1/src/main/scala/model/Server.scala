package model

import cats.Monad.ops.toAllMonadOps
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import repository.TweetRepositoryImpl
import service.TweetService
import cats.syntax.monad._

import scala.concurrent.ExecutionContext
object Server extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    val tweetRepository = new TweetRepositoryImpl()
    val tweetService = new TweetService(tweetRepository)
    val routes = new Routes(tweetService)
    val apis = routes.allRoutes.orNotFound

    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(apis)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
