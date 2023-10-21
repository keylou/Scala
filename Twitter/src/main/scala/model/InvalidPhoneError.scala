package model

case class InvalidPhoneError(phone: String) extends Exception {
  override def getMessage: String = s"Invalid phone ${phone}"
}
