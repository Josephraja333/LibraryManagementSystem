package org.example.ui

import org.example.entities.book.Book
import org.example.dataManager.BookDataManager
import org.example.dataManager.LibrarianDataManager
import entities.users.Admin
import org.example.dataManager.MemberDataManager
import org.example.util.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object AdminUI {
    fun displayLibrarians() {
        val librarians = LibrarianDataManager.getLibrarians()
        if(librarians.isEmpty()){
            println(NO_LIBRARIANS_AVAILABLE)
            return
        }
        librarians.forEachIndexed { index, librarian -> println("${index + 1}.${librarian.name} ID:${librarian.librarianID}") }
    }

    fun checkSubscriptionPaidBy() {
        val members = MemberDataManager.getMembers().filter { ChronoUnit.DAYS.between(it.paymentInfo.lastSubscriptionPaymentDate, LocalDate.now()) <=7 }
        members.forEach { println("Subscription paid by member ${it.name}") }
    }

    fun removeBook() {
        while (true) {
            val books = BookDataManager.getBooks()
            if(books.isEmpty()) {
                println("Library is empty")
                return
            }
            books.forEachIndexed { index, book ->  println("${index+1}.${book.bookName}")}
            val choice = CommonUtil.getInput("Choose a book to remove\n0 to Exit\n") { it.toInt() }.takeIf { it!=0 }?:return
            if(choice>books.size){
                println(ENTER_VALID_OPTION)
                continue
            }

            if (MemberDataManager.getMembers()
                    .flatMap { it.borrowingInfo.currentlyBorrowedBooks.map { borrowedBook -> borrowedBook.bookName } }
                    .contains(books[choice-1].bookName)) {
                println("Someone already borrowed the book, so it can't be removed for now")
            }
            else {
                BookDataManager.removeBook(books[choice-1])
                println("Book removed successfully")
            }

            break
        }
    }

    fun addBookToLibrary() {
        println("Enter Book Name")
        val bookName = readln()
        println("Enter Author Name")
        val authorName = readln()
        println("Enter Book Category")
        val category = readln()
        BookDataManager.setBook(Book(bookName, authorName, category))
    }

    fun fireLibrarian() {
        println()
        val librarians = LibrarianDataManager.getLibrarians()
        if(librarians.isEmpty()){
            println(NO_LIBRARIANS_AVAILABLE)
            return
        }
        println("Ent er the corresponding number to remove librarian\n0 to Exit")
        librarians.forEachIndexed { index, librarian -> println("${index+1}.${librarian.name}") }

        val index = CommonUtil.getInput("") { it.toInt() }.takeIf { it != 0 && it <= librarians.size } ?: run {
            println("Option is Invalid")
            return
        }
        LibrarianDataManager.removeLibrarian(librarians[index-1])
        println("Librarian Fired")
    }

    fun printUserDetails() =
        println("Name: ${Admin.name}\nID: ${Admin.adminID}\nPhone Number: ${Admin.phoneNumber}")

    fun showCardBalance() = Admin.showCardBalance()

    fun settings() = Admin.settings()

    fun printFilteredBooks() = BooksUtil.printFilteredBooks()

    fun searchBooks() = BooksUtil.displayBooksBySearch()

    fun showAllBooks() = BooksUtil.showAllBooks()

    fun displayMembers() = StaffUtil.displayMembers()

    fun showMemberDetails() = StaffUtil.getMemberDetails()
}