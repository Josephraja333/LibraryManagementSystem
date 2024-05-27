package entities.users
import org.example.ui.UserAuthUI

abstract class UserAccountDetails(
    var userID: String = "",
    var password: String = "",
    var phoneNumber: String = "",
    var cardNumber: String = "",
    var cardBalance: Int = 0
) : UserSettings {

    constructor(memberId: String) : this() {
        userID = memberId
        phoneNumber = UserAuthUI.getPhoneNumberInput()
        password = UserAuthUI.getPasswordInput()
        cardBalance = (5000..10000).random()
        cardNumber = getCardNumberInput()
        println("This is you Login ID: $memberId")
    }

    override fun changePassword() {
        while (true) {
            val currentPassword = UserAuthUI.getPasswordInput()
            if (currentPassword == password) break
            println("Password you've entered doesn't match the old one")
        }
        println("Enter new password")
        password = UserAuthUI.getPasswordInput()
        println("Password Changed Successfully")
    }

    override fun changePhoneNumber() {
        phoneNumber = UserAuthUI.getPhoneNumberInput()
        println("Phone Number Changed Successfully")
    }

    override fun changeCardNumber() {
        cardNumber = getCardNumberInput()
        println("Card Number Changed Successfully")
    }

    private fun getCardNumberInput() = generateSequence {
        UserAuthUI.getInput("Enter your Credit/Debit Card number: ") { it.toLong() }
            .toString()
            .also { if (it.length != 16) println("Enter a valid card number") }
    }.first { it.length == 16 }

    override fun showCardBalance() = println(cardBalance.toString() + "rs")

    override fun settings() {
        while (true) {
            println()
            println("Enter the corresponding number to the options\n0 to Exit")
            println("1.Change your Password")
            println("2.Change your Phone Number")
            println("3.Change your Credit/Debit Number")
            when (readln()) {
                "1" -> changePassword()
                "2" -> changePhoneNumber()
                "3" -> changeCardNumber()
                "0" -> break
                else -> println("Enter a valid option")
            }
        }
    }
}