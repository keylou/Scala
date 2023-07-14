import dao.TweetDao
import dto.Tweet
import org.scalatest.funsuite.AnyFunSuite
import dao.impl.TweetDaoImpl
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, times, verify, when}
import org.scalatestplus.mockito.MockitoSugar
import service.TweetService


class MainTest extends AnyFunSuite with MockitoSugar{

  test("dao save") {
    println("dao save")
    val dao = new TweetDaoImpl
    val tweet = Tweet("Test", "name")
    dao.save(tweet)
    assert(dao.getFeed.contains(1))
  }

  test("service save") {
    println("service save")
    val dao = mock[TweetDao]
    val service = new TweetService(dao)

    var tweet = Tweet("Test", "name")
    val tweetAns = Tweet("Test", "name",1)

    when(dao.save(tweet)).thenReturn(tweetAns)

    tweet = service.save(tweet)

    assert(tweet.id.equals(1))
    verify(dao, times(1)).save(any())
  }
}
