package com.example.bankaccountquiz

class BankAccount(var accountHolder: String,
                  var balance:Double) {

    fun acctBalance(): Double { return balance }
    private val transactions = mutableListOf<String>()



    fun deposit(amount:Double){
        balance+=amount
        transactions.add("${accountHolder} deposited $amount\n")
    }
    fun withdraw(amount:Double){
        if (amount <= balance) {
            balance -= amount
            transactions.add("${accountHolder} withdraw $amount")
        } else {
            println("Yetersiz bakiye")
        }
    }

    fun transactionsHistory(){
        // Listeyi virgüllerle ayırarak bir string oluşturun
        val transactionHistory = transactions.joinToString(", ")
        println("$accountHolder's transactions history: $transactionHistory "
        +
        "$accountHolder's balance is ${acctBalance()}")
    }
}