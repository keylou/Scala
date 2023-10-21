package model

case class InvalidEmailError(email: String) extends Exception {
  override def getMessage: String = s"Invalid email ${email}"
}
