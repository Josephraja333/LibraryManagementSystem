package entities.users
import org.example.ui.UserAuthUI
import org.example.util.CommonUtil

abstract class UserAccountDetails(
    var name: String = "",
    var password: String = "",
    var phoneNumber: String = "",
    var cardNumber: String = "",
    var cardBalance: Int = 0,
)  {

    constructor(memberID : String) : this() {
        this.name = UserAuthUI.getUserNameInput()
        phoneNumber = UserAuthUI.getPhoneNumberInput()
        password = UserAuthUI.getPasswordInput()
        cardBalance = (5000..10000).random()
        cardNumber = getCardNumberInput()
        println("This is your Member Login ID: $memberID")
    }

    private fun changePassword() {
        while (true) {
            val currentPassword = UserAuthUI.getPasswordInput()
            if (currentPassword == password) break
            println("Password you've entered doesn't match the old one")
        }
        println("Enter new password")
        password = UserAuthUI.getPasswordInput()
        println("Password Changed Successfully")
    }

    private fun changePhoneNumber() {
        phoneNumber = UserAuthUI.getPhoneNumberInput()
        println("Phone Number Changed Successfully")
    }

    private fun changeName() {
        phoneNumber = UserAuthUI.getUserNameInput()
        println("User Name Changed Successfully")
    }

    private fun changeCardNumber() {
        cardNumber = getCardNumberInput()
        println("Card Number Changed Successfully")
    }

    private fun getCardNumberInput() = generateSequence {
        CommonUtil.getInput("Enter your Credit/Debit Card number: ") { it.toLong() }
            .toString()
            .also { if (it.length != 16) println("Enter a valid card number") }
    }.first { it.length == 16 }

    fun showCardBalance() = println(cardBalance.toString() + "rs")

    fun settings() {
        while (true) {
            println()
            println("Enter the corresponding number to the options\n0 to Exit")
            println("1.Change your Name")
            println("2.Change your Password")
            println("3.Change your Phone Number")
            println("4.Change your Credit/Debit Number")
            when (readln()) {
                "1" -> changeName()
                "2" -> changePassword()
                "3" -> changePhoneNumber()
                "4" -> changeCardNumber()
                "0" -> break
                else -> println("Enter a valid option")
            }
        }
    }
}