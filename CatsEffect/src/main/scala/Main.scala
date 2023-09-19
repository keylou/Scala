import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.kernel.{ParallelF, Ref}
import cats.effect.std.Mutex

import java.util.concurrent.{Executors, LinkedBlockingQueue, ThreadPoolExecutor, TimeUnit}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt
class pipeLineImpl(mutex: Mutex[IO],
                   N: Int, K:Int,
                   listOfArrays: Ref[IO, List[Array[Int]]],
                   list: Ref[IO, List[Int]],
                   result:Ref[IO, Int]) {
  def generator(): IO[Unit] = IO {
    IO.sleep(N.second)
    val pack = Array.fill(10)(scala.util.Random.nextInt(10))
    listOfArrays.update(l => l :+ pack)
    generator()
    println(s"generator = ${Thread.currentThread().getName}")
  }

  def handler(): IO[Unit] = IO {
    mutex.lock.surround {
      for {
        a <- listOfArrays.get
        _ <- list.update(l => l ++ a.flatMap(arr => arr.sorted.drop(7).toList))
        _ <- listOfArrays.set(List.empty)
      } yield ()
    }
    IO.sleep(N.second)
    handler()
    println(s"handler = ${Thread.currentThread().getName}")
  }
  def accum(): IO[Unit] = IO {
    mutex.lock.surround {
      for {
        a <- list.get
        _ <- result.update(i => i + a.sum)
        _ <- list.set(List.empty)
      } yield ()
    }
    IO.sleep(N.second)
    accum()
    println(s"accum = ${Thread.currentThread().getName}")
  }
  def publicator(): IO[Unit] = IO {
    println(result.get)
    IO.sleep(K.second)
    publicator()
    println(s"publicator = ${Thread.currentThread().getName}")
  }
}
object pipeLine {

  val N = scala.io.StdIn.readLine().toInt
  val K = scala.io.StdIn.readLine().toInt
  val listOfArrays = Ref.of[IO, List[Array[Int]]](List[Array[Int]]())
  val list = Ref.of[IO, List[Int]](List[Int]())
  val result = Ref.of[IO, Int](0)
  def make: IO[pipeLineImpl] = {
    for {
      m <- Mutex[IO]
      loa <- listOfArrays
      l <- list
      r <- result
    } yield new pipeLineImpl(m, N, K, loa, l, r)
  }
}
object MainIO extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val M = scala.io.StdIn.readLine().toInt

    val ec1 = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))

    for {
      pipe <- pipeLine.make
      _ <- pipe.generator()
      _ = for (i <- 1 to M)
          pipe.handler ()
      _ <- pipe.accum()
      _ <- pipe.publicator()
    } yield ExitCode.Success
  }
}