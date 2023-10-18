import cats.effect.implicits._
import cats.effect.kernel.Ref
import cats.effect.std.Mutex
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

trait PipelineAcc {

  def run(
      ec1: ExecutionContext,
      ec2: ExecutionContext,
      ec3: ExecutionContext,
      ec4: ExecutionContext
  ): IO[Unit]

}

class PipelineAccImpl(
    mutex: Mutex[IO],
    N: Int,
    K: Int,
    M: Int,
    listOfArrays: Ref[IO, List[Array[Int]]],
    list: Ref[IO, List[Int]],
    result: Ref[IO, Int]
) extends PipelineAcc {
  private def generator(): IO[Unit] =
    for {
      _ <- IO.sleep(N.second)
      pack = Array.fill(10)(scala.util.Random.nextInt(10))
      _ <- listOfArrays.update(l => l :+ pack)
      _ = println(s"generator = ${Thread.currentThread().getName}")
      _ <- generator()
    } yield ()

  private def handler(): IO[Unit] =
    for {
      _ <- mutex.lock.surround {
        for {
          a <- listOfArrays.get
          _ <- list.update(l =>
            l ++ a.flatMap(arr => arr.sorted.drop(7).toList)
          )
          _ <- listOfArrays.set(List.empty)
        } yield ()
      }
      _ <- IO.sleep(N.second)
      _ = println(s"handler = ${Thread.currentThread().getName}")
      _ <- handler()
    } yield ()

  private def accum(): IO[Unit] =
    for {
      _ <- mutex.lock.surround {
        for {
          a <- list.get
          _ <- result.update(i => i + a.sum)
          _ <- list.set(List.empty)
        } yield ()
      }
      _ <- IO.sleep(N.second)
      _ = println(s"accum = ${Thread.currentThread().getName}")
      _ <- accum()
    } yield ()

  private def publicator(): IO[Unit] =
    for {
      r <- result.get
      _ = println(r)
      _ <- IO.sleep(K.second)
      _ = println(s"publicator = ${Thread.currentThread().getName}")
      _ <- publicator()
    } yield ()

  def f =
    for {
      p <- IO(100).start
      p2 <- IO("sdfsdfsd").start
      _ = println("dsfds")
      _ <- p.cancel
      res <- p.join
      res2 <- p.join
    } yield ()

  override def run(
      ec1: ExecutionContext,
      ec2: ExecutionContext,
      ec3: ExecutionContext,
      ec4: ExecutionContext
  ): IO[Unit] =
    (List(generator().evalOn(ec1), accum().evalOn(ec3), publicator().evalOn(ec4)) ++ List.fill(M)(
      handler().evalOn(ec2)
    )).parSequence.void

}
object PipeLine {

  val N = 1
  val K = 2
  val M = 2
  val listOfArrays = Ref.of[IO, List[Array[Int]]](List[Array[Int]]())
  val list = Ref.of[IO, List[Int]](List[Int]())
  val result = Ref.of[IO, Int](0)
  def make: IO[PipelineAccImpl] = {
    for {
      m <- Mutex[IO]
      loa <- listOfArrays
      l <- list
      r <- result
    } yield new PipelineAccImpl(m, N, K, M, loa, l, r)
  }
}
object MainPipeline extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {

    val ec1 = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
    val ec2 = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))
    val ec3 = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
    val ec4 = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())

    for {
      pipeline <- PipeLine.make
      _ <- pipeline.run(ec1, ec2, ec3, ec4)
    } yield ExitCode.Success
  }
}
