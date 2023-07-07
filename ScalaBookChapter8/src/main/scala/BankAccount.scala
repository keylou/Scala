class BankAccount(initialBalance: Double) {
  private var balance = initialBalance
  def currentBalance = balance
  def deposit(amount: Double) = { balance += amount; balance }
  def withdraw(amount: Double) = { balance -= amount; balance }
}
class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance) {
  var operationtimes = 0;
  def earnMonthlyInterest = {
    operationtimes = 0;
    super.deposit(super.currentBalance*0.15)
  }
  override def deposit(amount: Double) = {
    if (operationtimes > 3)
      {
        super.deposit(amount - 1)
      }
    else
      {
        operationtimes += 1
        super.deposit(amount)
      }
  }
  override def withdraw(amount: Double) = {
    if (operationtimes > 3) {
      super.withdraw(amount+1)
    }
    else {
      operationtimes += 1
      super.withdraw(amount)
    }
  }
}