package com.example.rockpaperscissors

fun main() {
    while (true) {  // Sonsuz döngü başlatıyoruz, doğru giriş yapılana kadar döner.
        println("rock, paper or scissors? Enter Your Choice:")
        val playerChoice = readln().trim().lowercase()  // Kullanıcının girdiği tercihi al

        // Geçerli seçimleri kontrol et
        if (playerChoice != "rock" && playerChoice != "paper" && playerChoice != "scissors") {
            println("Invalid character, please enter rock, paper, or scissors.")
            continue  // Geçersiz seçimde döngüye geri dön
        }

        // Bilgisayarın seçim yapması
        val randomNumber = (1..3).random()
        val computerChoice = when (randomNumber) {
            1 -> "rock"
            2 -> "paper"
            3 -> "scissors"
            else -> throw IllegalStateException("Unexpected value: $randomNumber")
        }

        // Kazananı belirleme
        val winner = when {
            playerChoice == computerChoice -> "Tie"
            playerChoice == "rock" && computerChoice == "scissors" -> "Player"
            playerChoice == "scissors" && computerChoice == "paper" -> "Player"
            playerChoice == "paper" && computerChoice == "rock" -> "Player"
            else -> "Computer"
        }

        // Sonucu yazdırma
        if (winner == "Tie") {
            println("It's a Tie!")
        } else {
            println("$winner won!")
        }

        // Kullanıcıya oyunu tekrar oynamak isteyip istemediğini sorabilirsiniz.
        println("Do you want to play again? (yes/no)")
        val playAgain = readln().trim().lowercase()
        if (playAgain != "yes") {
            break  // Kullanıcı "yes" demediyse döngüyü kır
        }
    }
}
