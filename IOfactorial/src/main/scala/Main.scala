import Factorial.threads
import cats.effect.{Deferred, ExitCode, IO, IOApp, Ref}
import cats.implicits._

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
trait FactorialAcc {
  def run(
           ec: ExecutionContext,
         ): IO[Unit]

  def getResult(): IO[Long]
}
class FactorialImpl(n:Int, part:Int, threads:Int, accum: Ref[IO,Long]) extends FactorialAcc {
  private def f(lower:Int, upper:Int): IO[Unit] = {
     (lower until upper).map(elem => accum.update(a => a * elem)).toList.sequence.void // IO[List[]]  void - опускает результат.
  }
  override def run(ec: ExecutionContext): IO[Unit] = {
    (0 to threads).map(i => f(i * part + 1, (i + 1) * part).evalOn(ec)).toList.parSequence.void
  }
  override def getResult(): IO[Long] = {
    accum.get
  }
}

object Factorial {
  val n = scala.io.StdIn.readLine().toInt
  val threads = scala.io.StdIn.readLine().toInt

  val part = n/threads

  val accum = Ref.of[IO,Long](1)
  def make: IO[FactorialAcc] = {
    for {
      a <- accum
    } yield new FactorialImpl(n,part,threads,a)
  }

}
object MainIO extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(threads))

    (for {
        factorial <- Factorial.make
        _ <- factorial.run(ec)
        a <- factorial.getResult()
        _ = println(a)
      } yield ExitCode.Success).guarantee(IO(Runtime.getRuntime().halt(0)))
  }
}