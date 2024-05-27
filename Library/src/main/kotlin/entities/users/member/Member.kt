package entities.users.member

import entities.users.MemberAccountData
import entities.users.UserAccountDetails
import org.example.entities.book.Book
import org.example.ui.MemberUI
import org.example.util.MEMBER_ID
import java.time.LocalDate

data class Member(val name: String) : UserAccountDetails(generatedUserId),MemberAccountData {

    override val currentlyBorrowedBooks: MutableList<Book> = mutableListOf()
    override val booksRequested: MutableList<Book> = mutableListOf()
    override val history: MutableList<String> = mutableListOf()
    override val booksBorrowedTime: MutableMap<String, LocalDate> = mutableMapOf()

    override val helpAskedByOtherMembers: MutableList<String> = mutableListOf()
    override val helpRequested: MutableList<String> = mutableListOf()

    override var lastSubscriptionPaymentDate: LocalDate = LocalDate.now()
    override var currentFineAmount: Int = 0
    override var memberStatus: MemberStatus = MemberStatus.AVAILABLE

    companion object {
        private var id = MEMBER_ID

        val generatedUserId: String
            get() = "M${id++}"
    }

    fun checkSubscriptionValidity() = MemberUI.checkSubscriptionValidity(this)

    fun searchBook() = MemberUI.searchBook(this)

    fun helpMember() = MemberUI.helpMember(this)

    fun getBookFromLibrary() = MemberUI.getBookFromLibrary(this)

    fun returnBook() = MemberUI.returnBook(this)

    fun requestNewBook() = MemberUI.requestNewBook(userID,name)

    fun printBorrowedBooks() = MemberUI.printBorrowedBooks(this)

    fun printHistory() = MemberUI.printHistory(this)

    fun printUserDetails() = MemberUI.printUserDetails(this)
}