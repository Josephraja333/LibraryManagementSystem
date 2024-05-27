package org.example

import entities.users.Admin
import entities.users.Librarian
import entities.users.member.Member
import entities.users.member.MemberStatus
import org.example.ui.AdminUI
import org.example.util.BooksUtil
import org.example.ui.UserAuthUI
import org.example.util.*

fun main() {
    while (true) {
        println("\nEnter the corresponding number to select\n0.Exit\n1.Admin\n2.Librarian\n3.Member")
        when (readln()) {
            "1" -> loginAdmin()
            "2" -> loginLibrarian()
            "3" -> loginMember()
            "0" -> break
            else -> println(ENTER_VALID_OPTION)
        }
        println()
    }
}

fun loginAdmin() {
    println("Enter Admin ID:")
    val adminID = readln().let { if (!it.contains("A",true)) "A$it" else it }
    println("Enter Password:")
    val password = readln()
    if (adminID.equals(Admin.userID, true) && password == Admin.password) adminFunctions()
    else println("Your Password is incorrect try again")
}

fun adminFunctions() {
    println("Logging in.....")
    Admin.checkSubscriptionPaidBy()

    while (true) {
        println("\nEnter the corresponding number to the options\n0.Exit\n1.Show a specific member's details\n2.Show all members\n3.Show all librarians\n4.Fire a librarian\n5.Show all books\n6.Search book\n7.Filter books\n8.Add books to library\n9.Credit salary to librarians\n10.Show card balance\n11.Remove book\n12.Check add book requests\n13.Show My Details\n14.Settings")

        when (readln()) {
            "1" -> StaffUtil.getMemberDetails()
            "2" -> StaffUtil.displayMembers()
            "3" -> AdminUI.displayLibrarians()
            "4" -> Admin.fireLibrarian()
            "5" -> BooksUtil.showAllBooks()
            "6" -> BooksUtil.searchBooks()
            "7" -> BooksUtil.printFilteredBooks()
            "8" -> Admin.addBookToLibrary()
            "9" -> Admin.creditSalary()
            "10" -> Admin.showCardBalance()
            "11" -> Admin.removeBook()
            "12" -> Admin.checkAddBookRequests()
            "13" -> Admin.printUserDetails()
            "14" -> Admin.settings()
            "0" -> break
            else -> println(ENTER_VALID_OPTION)
        }
    }
    println("Closing....")
}

fun loginLibrarian() {
    while (true) {
        println("Enter the corresponding number to select\n0.Exit\n1.Login")
        when (readln()) {
            "0" -> break
            "1" -> {
                UserAuthUI.loginLibrarian()?.let { librarianFunctions(it) }
                break
            }
            else -> println(ENTER_VALID_OPTION)
        }
    }
}

fun librarianFunctions(librarian: Librarian) {
    librarian.checkSalaryCredited()

    while (true) {
        println("\nEnter the corresponding number to the options:\n0.Exit\n1.Show all members\n2.Show a specific member's details\n3.Show all books\n4.Search book\n5.Filter books\n6.Check card balance\n7.Check book requests\n8.Ban member\n9.Check add book requests\n10.Add book to library\n11.Show My Details\n12.Settings")

        when (readln()) {
            "0" -> break
            "1" -> StaffUtil.displayMembers()
            "2" -> StaffUtil.getMemberDetails()
            "3" -> BooksUtil.showAllBooks()
            "4" -> BooksUtil.searchBooks()
            "5" -> BooksUtil.printFilteredBooks()
            "6" -> librarian.showCardBalance()
            "7" -> librarian.checkBookRequests()
            "8" -> librarian.banMember()
            "9" -> librarian.checkAddBookRequests()
            "10" -> librarian.addBookToLibrary()
            "11" -> librarian.printUserDetails()
            "12" -> librarian.settings()
            else -> println(ENTER_VALID_OPTION)
        }
    }
}

fun loginMember() {
    while (true) {
        println("Enter the corresponding number to select\n0.Exit\n1.Create\n2.Login")
        when (readln()) {
            "0" -> break
            "1" -> {
                UserAuthUI.createMember()
                break
            }

            "2" -> {
                UserAuthUI.loginMember()?.let { memberFunctions(it) }
                break
            }

            else -> println(ENTER_VALID_OPTION)
        }
    }
}

fun memberFunctions(member: Member) {
    if (member.helpAskedByOtherMembers.size != 0) member.helpMember()
    member.checkSubscriptionValidity()

    while (member.memberStatus == MemberStatus.AVAILABLE) {
        println("\nEnter the corresponding number to the options\n0.Exit\n1.Show Borrowed Books\n2.Get Book History\n3.Get Book\n4.Return a Book\n5.Search Book\n6.Check Card Balance\n7.Request Book\n8.Show My Details\n9.Settings")

        when (readln()) {
            "1" -> member.printBorrowedBooks()
            "2" -> member.printHistory()
            "3" -> member.getBookFromLibrary()
            "4" -> member.returnBook()
            "5" -> member.searchBook()
            "6" -> member.showCardBalance()
            "7" -> member.requestNewBook()
            "8" -> member.printUserDetails()
            "9" -> member.settings()
            "0" -> break
            else -> println(ENTER_VALID_OPTION)
        }
    }

    if (member.memberStatus == MemberStatus.TEMPORARILY_REMOVED) {
        println("You're temporarily removed")
        return
    }
}