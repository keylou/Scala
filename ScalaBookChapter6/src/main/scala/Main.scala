object Main {
  // Объект одиночка
  private var lastNumber = 0
  def newUniqueNumber() = {
    lastNumber += 1; lastNumber
  }

  //Когда в приложении потребуется получить новый уникальный учетный номер, достаточно вызвать Accounts.newUniqueNumber().
}

class Account {
  val id = Account.newUniqueNumber()
  private var balance = 0.0
  def deposit(amount: Double) { balance += amount }
}
object Account { // Объект-компаньон (имеет такое же название, как у класса.)
  // могут обращаться к методам друг друга
  private var lastNumber = 0
  private def newUniqueNumber() = { lastNumber += 1; lastNumber }
}

// Объект может расширять класс
abstract class UndoableAction(val description: String) {
  def undo(): Unit
  def redo(): Unit
}
object DoNothingAction extends UndoableAction("Do nothing") {
  override def undo() {}
  override def redo() {}
}
// val actions = Map("open" -> DoNothingAction, "save" -> DoNothingAction, ...)
// Операции открытия и сохранения еще не реализованы
object TrafficLightColor extends Enumeration { // тип-перечисление
  val Red, Yellow, Green = Value
  // На значения перечисления можно ссылаться как TrafficLightColor.Red, TrafficLightColor.Yellow ...
  // Либо import TrafficLightColor._

  // Оба вернут объект TrafficLightColor.Red
  // TrafficLightColor(0) // Вызовет Enumeration.apply
  // TrafficLightColor.withName("Red")
}