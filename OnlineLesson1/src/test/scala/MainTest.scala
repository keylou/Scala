import dto.Tweet
import org.scalatest.funsuite.AnyFunSuite
import dao.impl.TweetDaoImpl
import org.scalatestplus.mockito.MockitoSugar


class MainTest extends AnyFunSuite with MockitoSugar{

  test("dao save") {
    println("dao save")
    val dao = new TweetDaoImpl
    val tweet = Tweet("Test", "name")
    dao.save(tweet)
    assert(dao.getFeed.contains(1))
  }
}
