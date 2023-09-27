import TestUtils.UserId

import scala.annotation.tailrec
import scala.concurrent.{ExecutionContext, Future}

object Main3 extends App {

  var s = 0

  val list = List(1, 2, 3)
  val list2 = 1 :: list

  @tailrec
  def sum(list: List[Int], acc: Int = 0): Int = {
    list match {
      case ::(head, tail) => sum(tail, acc + head)
      case Nil => acc
    }
  }
  f()
  def f() = {
    val userId = UserId("13123")
    val phoneNumber = PhoneNumber("00000")

    val result = for {
      user <- userId
      //phone <- phoneNumber
    } yield getUser(user, phoneNumber)

    sealed trait MyOption[+A]
    case object MyNone extends MyOption[Nothing]
    case class MySome[A](value: A) extends MyOption[A]



    sealed trait MyTry[+A]
    case class MyError(error: Throwable) extends MyTry[Nothing]
    case class MySuccess[A](value: A) extends MyTry[A]
    object MyTry {
      def flatMap[A,B](mytry: MyTry[A])(f: A => MyTry[B]): MyTry[B] =
        mytry match {
          case MyError(error) => MyError(error)
          case MySuccess(value) => f(value)
        }
      def map[A,B](mytry: MyTry[A])(f: A => B): MyTry[B] =
        mytry match {
          case MyError(error) => MyError(error)
          case MySuccess(value) => MySuccess(f(value))
        }


    }




    def ff(s: String): String => String = { x1: String =>
      x1 + s
    }

    val ffff: String => String = ff("asdfas")

    object MyOption {
      def flatMap[A, B](option: MyOption[A])(f: A => MyOption[B]): MyOption[B] =
        option match {
          case MyNone => MyNone
          case MySome(value) => f(value)
        }

      def map[A, B](option: MyOption[A])(f: A => B): MyOption[B] =
        option match {
          case MyNone => MyNone
          case MySome(value) => MySome(f(value))
        }
    }

    object MyEither {
      def flatMap[A, B, E](either: Either[E, A])(f: A => Either[E, B]): Either[E, B] =
        either match {
          case Left(error) => Left(error)
          case Right(value) => f(value)
        }
    }

    val myOptionInt: MyOption[Int] = MySome(123)
    val mytry: MyTry[String] = MySuccess("Correct")
    val res: MyTry[String] = MyTry.map(mytry) { x =>
      x + "ation"
    }
    val res2: MyTry[String] = MyTry.flatMap(mytry) { x =>
      if (x.length>4) MyError("error!") else MySuccess(x+"!")
    }
    println(res2)
  }

  def getUser(userId: UserId, phoneNumber: PhoneNumber): String = ???
  def getUser2(userId: String, phoneNumber: String): String = ???
  def getUser3(userId: UserId, state: StatusUser): String = ???

  sealed trait StatusUser
  case object Offline extends StatusUser
  case object Online extends StatusUser

  case class PhoneNumber(value: String)

//  implicit val ec: ExecutionContext = ExecutionContext.global
//
//  val x: IO[Int] = IO {
//    println("hello")
//    10 + 12
//  }
//
//  val y1 = for {
//    x1 <- x
//    x2 <- x
//  } yield x1 + x2
//
//  //  val x: Int = 10 + 12
//
//  val y1 = for {
//    x1 <- IO {
//      println("hello")
//      10 + 12
//    }
//    x2 <- IO {
//      println("hello")
//      10 + 12
//    }
//  } yield x1 + x2
//
//  val y2 = {
//    println("hello")
//    10 + 12
//  } * {
//    println("hello")
//    10 + 12
//  }

}

object TestUtils {

  case class UserId private (value: String)

  object UserId {
    def apply(value: String): Either[String, UserId] = {
      if (value.exists(x => !x.isLetter))
        Left("Error")
      else {
        Right(new UserId(value))
      }
    }
  }

}
