package org.example

import entities.users.Librarian
import entities.users.member.Member
import org.example.ui.AdminUI
import org.example.ui.LibrarianUI
import org.example.ui.MemberUI
import org.example.ui.UserAuthUI
import org.example.util.*

fun main() {
    while (true) {
        println("\nEnter the corresponding number to select\n0.Exit\n1.Admin\n2.Librarian\n3.Member")
        when (readln()) {
            "1" -> loginAdmin()
            "2" -> loginLibrarian()
            "3" -> loginOrCreateUser()
            "0" -> break
            else -> println(ENTER_VALID_OPTION)
        }
        println()
    }
}

fun loginAdmin() {
    if(UserAuthUI.loginAdmin()) adminFunctions()
}

fun adminFunctions() {
    println("Logging in.....")
    AdminUI.checkSubscriptionPaidBy()

    while (true) {
        println("\nEnter the corresponding number to the options\n0.Exit\n1.Show a specific member's details\n2.Show all members\n3.Show all librarians\n4.Fire a librarian\n5.Show all books\n6.Search book\n7.Filter books\n8.Add books to library\n9.Show card balance\n10.Remove book\n11.Show My Details\n12.Settings")

        when (readln()) {
            "1" -> AdminUI.showMemberDetails()
            "2" -> AdminUI.displayMembers()
            "3" -> AdminUI.displayLibrarians()
            "4" -> AdminUI.fireLibrarian()
            "5" -> AdminUI.showAllBooks()
            "6" -> AdminUI.searchBooks()
            "7" -> AdminUI.printFilteredBooks()
            "8" -> AdminUI.addBookToLibrary()
            "9" -> AdminUI.showCardBalance()
            "10" -> AdminUI.removeBook()
            "11" -> AdminUI.printUserDetails()
            "12" -> AdminUI.settings()
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
    println("Logging in.....")
    while (true) {
        println("\nEnter the corresponding number to the options:\n0.Exit\n1.Show all members\n2.Show a specific member's details\n3.Show all books\n4.Search book\n5.Filter books\n6.Check card balance\n7.Check book requests\n8.Ban member\n9.Check add book requests\n10.Add book to library\n11.Show My Details\n12.Settings")

        when (readln()) {
            "0" -> break
            "1" -> LibrarianUI.displayMembers()
            "2" -> LibrarianUI.showMemberDetails()
            "3" -> LibrarianUI.showAllBooks()
            "4" -> LibrarianUI.searchBooks()
            "5" -> LibrarianUI.printFilteredBooks()
            "6" -> LibrarianUI.showCardBalance(librarian)
            "7" -> LibrarianUI.checkBookRequests()
            "8" -> LibrarianUI.banMember()
            "9" -> LibrarianUI.checkAddBookRequests()
            "10" -> LibrarianUI.addBookToLibrary()
            "11" -> LibrarianUI.printUserDetails(librarian)
            "12" -> LibrarianUI.settings(librarian)
            else -> println(ENTER_VALID_OPTION)
        }
    }
}

fun loginOrCreateUser() {
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
    println("Logging in.....")
    if (member.helpInfo.helpAskedByOtherMembers.size != 0) MemberUI.helpMember(member)
    MemberUI.checkSubscriptionValidity(member)

    while (true) {
        println("\nEnter the corresponding number to the options\n0.Exit\n1.Show Borrowed Books\n2.Get Book History\n3.Get Book\n4.Return a Book\n5.Search Book\n6.Check Card Balance\n7.Request Book\n8.Show My Details\n9.Settings")

        when (readln()) {
            "1" -> MemberUI.printBorrowedBooks(member)
            "2" -> MemberUI.printHistory(member)
            "3" -> MemberUI.getBookFromLibrary(member)
            "4" -> MemberUI.returnBook(member)
            "5" -> MemberUI.searchBook(member)
            "6" -> MemberUI.showCardBalance(member)
            "7" -> MemberUI.requestNewBook(member.memberID,member.name)
            "8" -> MemberUI.printUserDetails(member)
            "9" -> MemberUI.settings(member)
            "0" -> break
            else -> println(ENTER_VALID_OPTION)
        }
    }
}