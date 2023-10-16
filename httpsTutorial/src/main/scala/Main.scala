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
import scala.util.Try
object Main extends IOApp {

  // My little imdb

  type Actor = String
  case class Movie(id:String, title: String, year:Int, actors: List[Actor], director: String)
  case class Director(firstName: String, lastName: String) {
    override def toString = s"$firstName $lastName"
  }
  case class DirectorDetails(firstName: String, lastName: String, genre: String)

  val snjl: Movie = Movie(
    "6bcbca1e-efd3-411d-9f7c-14b872444fce",
    "Zack Snyder's Justice League",
    2021,
    List("Henry Cavill", "Gal Godot", "Ezra Miller", "Ben Affleck", "Ray Fisher", "Jason Momoa"),
    "Zack Snyder"
  )
  val movies: Map[String, Movie] = Map(snjl.id -> snjl)
  private def findMovieById(movieId: UUID) =
    movies.get(movieId.toString)
  private def findMoviesByDirector(director: String): List[Movie] =
    movies.values.filter(_.director == director).toList

  // GET all movies for a director under given year
  // GET all actors for a movie
  // GET details of a director
  // POST add a new director

  // In general for a http4s application the scheme is Request -> F[Option[Response]],
  // for CatsEffect F stands for IO
  // HttpRoutes[F] is an analog of Request -> F[Option[Response]]

  // GET /movies?director=Zack%20Snyder&year=2021

  implicit val yearQueryParamDecoder: QueryParamDecoder[Year] =
    QueryParamDecoder[Int].emap(yearInt =>
      Try(Year.of(yearInt))
        .toEither
        .leftMap{e =>
          ParseFailure(e.getMessage, e.getMessage)
        }
    )
  object DirectorQueryParamMatcher extends QueryParamDecoderMatcher[String]("director")
  object YearQueryParamMatcher extends OptionalValidatingQueryParamDecoderMatcher[Year]("year")
  def movieRoutes[F[_] : Monad]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F] // gives some methods of F???
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "movies" :? DirectorQueryParamMatcher(director) +& YearQueryParamMatcher(maybeYear) =>
        val moviesByDirector = findMoviesByDirector(director)
        maybeYear match {
          case Some(validatedYear) =>
            validatedYear.fold(
              _ => BadRequest("The year was badly formatted"),
              year => {

                val moviesByDirectorAndYear = moviesByDirector.filter(_.year == year.getValue)
                Ok(moviesByDirectorAndYear.asJson)
              }
            )
          case None => Ok(moviesByDirector.asJson)
        }
      case GET -> Root / "movies" / UUIDVar(movieID) / "actors" =>
        findMovieById(movieID).map(_.actors) match {
          case Some(actors) => Ok(actors.asJson)
          case None => NotFound(s"No movie with id $movieID found in the data base")
        }
    }
  }

  object DirectorPath {
    def unapply(str: String): Option[Director] = {
      Try {
        val tokens = str.split(" ")
        Director(tokens(0), tokens(1))
      }.toOption
    }
  }

  val directorDetailsDB: mutable.Map[Director, DirectorDetails] =
    mutable.Map(Director("Zack","Snyder") -> DirectorDetails("Zack","Snyder","Fantasy"))
  def directorRoutes[F[_] : Monad]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "directors" / DirectorPath(director) =>
        directorDetailsDB.get(director) match {
          case Some(dirDetails) => Ok(dirDetails.asJson)
          case None => NotFound(s"No director $director found")
        }
    }
  }

  def allRoutes[F[_]: Monad]:HttpRoutes[F] =
    movieRoutes[F] <+> directorRoutes[F]

  def allRoutesComplete[F[_]: Monad]:HttpApp[F] = //simplified version of HttpRoutes
    allRoutes[F].orNotFound
  def run(args: List[String]): IO[ExitCode] = {
    val apis = Router(
      "/api" -> movieRoutes[IO],
      "/api/admin" -> directorRoutes[IO]
    ).orNotFound

    BlazeServerBuilder[IO](runtime.compute)
      .bindHttp(8080, "localhost") //alternative: apis
      .withHttpApp(apis)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
