package entities.users

import org.example.ui.LibrarianUI
import org.example.util.LIBRARIAN_CARD_BALANCE
import org.example.util.LIBRARIAN_ID
import java.time.LocalDate

data class Librarian(val name: String) : LibrarianWorkRecord, UserAccountDetails() {

    constructor(
        name: String,
        password: String,
        phoneNumber: String,
        cardNumber: String,
    ) : this(name) {
        this.userID = generatedUserId
        this.password = password
        this.phoneNumber = phoneNumber
        this.cardNumber = cardNumber
        this.cardBalance = LIBRARIAN_CARD_BALANCE
    }

    companion object{
        private var id = LIBRARIAN_ID

        val generatedUserId: String
            get() = "L${id++}"
    }

    override var isSalaryCredited = false
    override var daysWorked = LocalDate.now()

    fun checkSalaryCredited() = LibrarianUI.checkSalaryCredited(this)

    fun addBookToLibrary() = LibrarianUI.addBookToLibrary(userID,name)

    fun checkBookRequests() = LibrarianUI.checkBookRequests()

    fun checkAddBookRequests() = LibrarianUI.checkAddBookRequests()

    fun banMember() = LibrarianUI.banMember()

    fun printUserDetails() =  LibrarianUI.printUserDetails(this)
}