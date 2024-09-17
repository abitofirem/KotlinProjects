package com.example.bankaccountquiz

fun main() {
    var ba2 = BankAccount("İro", 20.0)
    println("${ba2.accountHolder}'s balance is ${ba2.acctBalance()}")
    var sa = BankAccount("Sarah", 0.0)
    sa.deposit(150.0)
    sa.withdraw(10.0)
    sa.transactionsHistory()


}