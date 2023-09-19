

import java.sql.Timestamp
import java.time.{Instant, LocalDateTime}
import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}
import java.util.concurrent.{Executors, LinkedBlockingQueue, SynchronousQueue, ThreadPoolExecutor, TimeUnit}
import scala.:+
import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.concurrent.duration._
import scala.math.BigInt
import scala.reflect.ClassTag

object Main extends App {

  implicit val ec1: ExecutionContext = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())

  implicit val ec2: ExecutionContext = ExecutionContext.fromExecutor(
    new ThreadPoolExecutor(
      1,
      10,
      100L,
      TimeUnit.SECONDS,
      new SynchronousQueue[Runnable]()
    )
  )

  val start = Instant.now()

  var state = 0

  val atomicResult = new AtomicInteger(0)


  val res1 = Future {
    Thread.sleep(1000)
    println(s"res1 = ${println(Thread.currentThread().getName)}")
    f(1 + 2)
    1 + 2
  }(ec1)
  val res2 = Future {
    Thread.sleep(1000)
    println(s"res2 = ${println(Thread.currentThread().getName)}")
    f(2 + 3)
    2 + 3
  }(ec2)
  val res3 = Future {
    Thread.sleep(1000)
    println(s"res3 = ${println(Thread.currentThread().getName)}")
    f(3 + 4)
    3 + 4
  }(ec2)

  private def f(delta: Int) = {
    synchronized {
      state = state + delta
    }
  }


  //  val result: Future[Int] = res1.flatMap(x1 => res2.flatMap(x2 => res3.map(x3 => x1 + x2 + x3)))

  val result = FutureUtils.calcResult(res1, res2, res3)(ec2)

  val r = Await.result(result, Duration.Inf)

  println(r)

  println(s"state = ${state}")

  println(Instant.now().getEpochSecond - start.getEpochSecond)


}

object exercises extends App {
  implicit val ec2: ExecutionContext = ExecutionContext.fromExecutor(
    new ThreadPoolExecutor(
      1,
      10,
      100L,
      TimeUnit.SECONDS,
      new SynchronousQueue[Runnable]()
    )
  )

  def doTogether1[T, U, V](f: T => Future[U], g: T => Future[V])(implicit ec2: ExecutionContext): T => Future[(U, V)] = { // возвращает функцию
    tArg: T => {
      val fFuture = f(tArg)
      val gFuture = g(tArg)
      for {
        t1 <- fFuture // достаю результат из future. t1: U
        t2 <- gFuture
      } yield (t1, t2)
    }
  } // задание 4

  def functionParametr(el: Int): Future[String] = {
    Future(el.toString)
  } // можно передавать не только анонимную функцию

  val function: Int => Future[(String, Double)] = doTogether1(functionParametr, (elem: Int) => Future(elem * 0.1)) // передача функций
  println(Await.result(function(2), Duration.Inf))

  def doTogether[T, U, V](f: T => Future[U], g: U => Future[V])(implicit ec2: ExecutionContext): T => Future[V] = {
    tArg: T =>
      for {
        t1 <- f(tArg)
        t2 <- g(t1)
      } yield t2
  } // задание 2

  def doTogether[T](f: Array[T => Future[T]])(implicit ec2: ExecutionContext): T => Future[T] = {
    tArg: T => {
      val accum = Future(tArg)
      f.foldLeft(accum)((ac, elem) => {
        for {
          t1 <- ac
          t2 <- elem(t1)
        } // for всегда вернет future от результата
        yield t2
      })
    }
  } // задание 3

  def doTogether3000[T: ClassTag](f: Array[Future[T]])(implicit ec2: ExecutionContext): Future[Array[T]] = {
    // использовать foldleft
    val accumulator = Future(Array.empty[T])
    f.foldLeft(accumulator)((ac, el) => {
      ac.flatMap(arr =>
        el.map(t => arr :+ t)
      )
    })
  } // задание 5

  // for {
  //        t1 <- ac
  //        t2 <- el
  //      } yield t1 :+ t2
  def countNumbers(n: Int, p: Int)(implicit ec2: ExecutionContext): Future[Int] = {
    val lengthOfPart = n / p
    val arrayP = Array.range(1, p)
    val arrayFutureP = arrayP.map((elem: Int) => Future {
      if (elem == p) {
        (lengthOfPart * (elem - 1) + 1).to(n)
          .toArray
          .foldLeft(0)((accum, el) => {
            val tmp: BigInt = el
            if (tmp.isProbablePrime(0))
              accum + 1
            else
              accum
          })
      }
      else {
        (lengthOfPart * (elem - 1) + 1).to(lengthOfPart * (elem - 1) + lengthOfPart)
          .toArray
          .foldLeft(0)((accum, el) => {
            val tmp: BigInt = el
            if (tmp.isProbablePrime(0))
              accum + 1
            else
              accum
          })
      }
    })

    val result = doTogether3000(arrayFutureP).map(a => a.foldLeft(0)(_ + _))
    Await.result(result, Duration.Inf)
    result
  }

  println(countNumbers(10, 3))
}

// функции высшего порядка, анонимные функции
object pipeLine extends App {
  // рандомная генерация массива из эл-тов от 0 до 9 ГОТОВО
  // распечатать, на каком пуле и тред потоке работает функция (есть вверху) ГОТОВО
  // должно работать M handler'ов ГОТОВО
  // массивы должны генерироваться быстрее, чем обрабатываются
  // handler должен быть потокобезопасный ГОТОВО????????

  // каждая функция должна работать на отдельном тред пуле (создать их)

  val N = scala.io.StdIn.readLine().toInt
  val K = scala.io.StdIn.readLine().toInt
  val M = scala.io.StdIn.readLine().toInt

  implicit val ecG: ExecutionContext = ExecutionContext.fromExecutor(
    new ThreadPoolExecutor(
      1,
      1,
      100L,
      TimeUnit.SECONDS,
    new LinkedBlockingQueue[Runnable] ()
    )
  )
  implicit val ecA: ExecutionContext = ExecutionContext.fromExecutor(
    new ThreadPoolExecutor(
      1,
      1,
      100L,
      TimeUnit.SECONDS,
      new LinkedBlockingQueue[Runnable]()
    )
  )
  implicit val ecP: ExecutionContext = ExecutionContext.fromExecutor(
    new ThreadPoolExecutor(
      1,
      1,
      100L,
      TimeUnit.SECONDS,
      new LinkedBlockingQueue[Runnable]()
    )
  )
  // generator accumulator publisher

  implicit val ecH: ExecutionContext = ExecutionContext.fromExecutor(
    new ThreadPoolExecutor(
      0,
      M,
      100L,
      TimeUnit.SECONDS,
      new SynchronousQueue[Runnable]()
    )
  ) // handler

  val atomicQueueOfArrays = new AtomicReference[mutable.Queue[Array[Int]]](mutable.Queue[Array[Int]]())
  val atomicQueue = new AtomicReference[mutable.Queue[Int]](mutable.Queue[Int]())
  val atomicResult = new AtomicInteger(0)
  def generator(): Unit = {
    Future {
      Thread.sleep(N)
      val pack = Array.fill(10)(scala.util.Random.nextInt(10))
      atomicQueueOfArrays.get().enqueue(pack)
      generator()
      println(s"generator = ${Thread.currentThread().getName}")
    }(ecG)
  }
  def handler(): Unit = {
    Future {
      if (atomicQueueOfArrays.get().nonEmpty) { 
        atomicQueue.getAndUpdate(a => a.enqueueAll(atomicQueueOfArrays.get().dequeue().sorted.drop(7)))
      }
      else
        Thread.sleep(N)
      handler()
      println(s"handler = ${Thread.currentThread().getName}")
    }(ecH)
  }
  def accum(): Unit = {
    Future {
      if (atomicQueue.get().nonEmpty)
        atomicResult.addAndGet(atomicQueue.get().dequeueAll(_ => true).sum)
      else
        Thread.sleep(N)
      accum()
      println(s"accum = ${Thread.currentThread().getName}")
    }(ecA)
  }
  def publicator(): Unit = {
    Future {
      println(atomicResult.get())
      Thread.sleep(K * 1000)
      publicator()
      println(s"publicator = ${Thread.currentThread().getName}")
    }(ecP)
  }

  generator()

  for (i <- 1 to M)
    handler()

  accum()

  publicator()
}
object FutureUtils {
  def calcResult(res1: Future[Int], res2: Future[Int], res3: Future[Int])(implicit ec2: ExecutionContext): Future[Int] =
    for {
      x1 <- res1
      x2 <- res2
      x3 <- res3
    } yield {
      x1 + x2 + x3
    }
}
