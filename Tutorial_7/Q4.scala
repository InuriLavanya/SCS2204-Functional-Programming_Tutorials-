object Q4 extends App {

  var accountList:List[Account_] = List()

  def accCreate(nic:String, accId: Int):Unit = {
    val acc = new Account_(nic, accId)
    accountList = accountList ::: acc :: Nil
    println("Account list = "+ accountList)
  }

  val find = (a:Int, b:List[Account_]) => b.filter(account => account.accId.equals(a))
  val overdraft = (b:List[Account_]) => b.filter(account => account.balance < 0.0)
  val totalBalance = (b:List[Account_]) => b.foldLeft(0.0)((x, y) => x + y.balance)
  val interest = (b:List[Account_]) => b.map(account => if(account.balance > 0) account.balance*0.05 else account.balance*0.1)


  /*              Driver Code                */

  //create accounts
  accCreate("1",1)
  accCreate("2",2)

  //deposit money
 // find(1, accountList)(0).deposit(1000)
 // println(find(1, accountList)(0))

  //transfer money
  find(1, accountList)(0).transfer(2, 100.0)
  println(find(2, accountList)(0))

  //list of negative balances
  println("Accounts with negative balances = " + overdraft(accountList))

  //sum of all account balances
  println("Total = " + totalBalance(accountList))

  //final balances of all accounts after apply the interest
  println("Interests of account list = " + interest(accountList))
}

class Account_(nic:String, val accId: Int, var balance: Double = 0.0){

  def withdrow(amount:Double) : Unit = {
    this.balance = this.balance - amount
  }

  def deposit(amount:Double) : Unit = {
    this.balance = this.balance + amount
  }

  def transfer(account:Int, amount:Double) : Unit = {
    val transferAcc = Q4.find(account, Q4.accountList)
    if (balance < 0.0) println("Insufficient balance")
    else {
      this.withdrow(amount)
      transferAcc(0).deposit(amount)
    }
  }

  override def toString = "["+nic+":"+accId +":"+ balance+"]"
}
