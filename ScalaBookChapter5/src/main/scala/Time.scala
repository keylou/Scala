class Time {
  private var hours = 0;
  private var minutes = 0;
  def this(hours: Int, minutes: Int) {
    this()
    this.hours = if (hours>23) 24 - hours else hours
    this.minutes = if (minutes>59) 60 - minutes else minutes
  }
  def before(other: Time): Boolean = {
    if (this.hours < other.hours | (this.hours == other.hours & this.minutes == other.minutes)) true else false
  }
  def info = s"Time is $hours:$minutes"
}