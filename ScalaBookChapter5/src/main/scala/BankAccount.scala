class BankAccount {
  private var balance = 0;
  def this(balance: Int) {
    this()
    this.balance = balance
  }
  def deposit (money: Int): Unit = {
    balance += money
  }
  def withdraw(money: Int): Unit = {
    balance -= money
  }
  def info = s"On your bank account $balance"
}
