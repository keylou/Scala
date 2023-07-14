package service

import dao.TweetDao
import dto.Tweet

class TweetService(private val dao:TweetDao) {

  def save(tweet: Tweet): Tweet = {
    dao.save(tweet)
  }
}
