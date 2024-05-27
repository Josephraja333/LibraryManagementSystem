package entities.users

import org.example.ui.AdminUI
import org.example.util.*

object Admin : AdminFeatures, UserAccountDetails(ADMIN_ID, ADMIN_PASSWORD, ADMIN_PHONE_NUMBER, ADMIN_CARD_NUMBER, ADMIN_CARD_BALANCE) {
    const val adminName = ADMIN_NAME

    override val addBookRequests = mutableListOf<String>()
    override val subscriptionPaidBy = mutableListOf<String>()

    fun checkSubscriptionPaidBy() = AdminUI.checkSubscriptionPaidBy()

    fun checkAddBookRequests() = AdminUI.checkAddBookRequests()

    fun fireLibrarian() = AdminUI.fireLibrarian()

    fun addBookToLibrary() = AdminUI.addBookToLibrary()

    fun creditSalary() = AdminUI.creditSalary()

    fun removeBook() = AdminUI.removeBook()

    fun printUserDetails() = AdminUI.printUserDetails()
}