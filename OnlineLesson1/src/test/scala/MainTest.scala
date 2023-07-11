import dto.Tweet
import org.scalatest.funsuite.AnyFunSuite
import dao.impl.TweetDaoImpl
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, times, verify, when}
import service.TweetService

class MainTest extends AnyFunSuite {

  test("dao save") {
    println("dao save")
    val dao = new TweetDaoImpl
    val tweet = Tweet("Test", "name")
    dao.save(tweet)
    assert(dao.getFeed.contains(1))
  }

  test("service save") {
    println("service save")
    val dao = mock(classOf[TweetDaoImpl])
    val service = new TweetService(dao)

    var tweet = Tweet("Test", "name")
    val tweetAns = Tweet("Test", "name")
    tweetAns.id = 1

    when(dao.save(any())).thenReturn(tweetAns)

    tweet = service.save(tweet)

    assert(tweet.id.equals(1))
    verify(dao, times(1)).save(any())
  }
}
