import scala.collection.mutable.ArrayBuffer
class Network {
  class Member(val name: String) {
    val contacts = new ArrayBuffer[Member]
    // каждый экземпляр получит собственный класс Member и поле members, если ArrayBuffer[Member]
    // ArrayBuffer[Network#Member] означает: «член (Member) любой группы (Network)»
  }
  private val members = new ArrayBuffer[Member]
  def join(name: String) = {
    val m = new Member(name)
    members += m
    m
  }
}