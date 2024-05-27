package entities.users
import entities.users.member.MemberStatus
import org.example.entities.book.Book
import java.time.LocalDate

interface LibrarianWorkRecord {
    var isSalaryCredited: Boolean
    var daysWorked: LocalDate
}

interface AdminFeatures {
    val addBookRequests :  MutableList<String>
    val subscriptionPaidBy :  MutableList<String>
}

interface MemberAccountData {
    val currentlyBorrowedBooks: MutableList<Book>
    val booksRequested: MutableList<Book>
    val history: MutableList<String>
    val booksBorrowedTime: MutableMap<String, LocalDate>

    val helpAskedByOtherMembers: MutableList<String>
    val helpRequested: MutableList<String>

    var lastSubscriptionPaymentDate: LocalDate
    var currentFineAmount: Int
    var memberStatus: MemberStatus
}

interface UserSettings {
    fun changePassword()
    fun changePhoneNumber()
    fun changeCardNumber()
    fun showCardBalance()
    fun settings()
}