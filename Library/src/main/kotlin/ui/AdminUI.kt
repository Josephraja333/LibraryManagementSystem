package org.example.ui

import org.example.entities.book.Book
import org.example.dataManager.BookDataManager
import org.example.dataManager.LibrarianDataManager
import entities.users.Admin
import org.example.util.AdminUtil
import org.example.util.ENTER_VALID_OPTION
import org.example.util.NO_LIBRARIANS_AVAILABLE
import org.example.util.SUBSCRIPTION_AMOUNT

object AdminUI {
    fun displayLibrarians() {
        val librarians = LibrarianDataManager.getLibrarians()
        if(librarians.isEmpty()){
            println(NO_LIBRARIANS_AVAILABLE)
            return
        }
        librarians.forEachIndexed { index, librarian -> println("${index + 1}.${librarian.name} ID:${librarian.userID}") }
    }

    fun checkSubscriptionPaidBy() {
        Admin.subscriptionPaidBy.forEach {
            println("Subscription paid by member $it")
            Admin.cardBalance += SUBSCRIPTION_AMOUNT
        }
        Admin.subscriptionPaidBy.clear()
    }

    fun checkAddBookRequests(){
        while (Admin.addBookRequests.isNotEmpty()) {
            println("Choose an option accept\n0 to Exit")
            Admin.addBookRequests.forEachIndexed { index, book ->
                book.split(" ")
                    .let { println("${index+1}.BookName=${it[0]}, AuthorName=${it[1]}, Category=${it[2]}") }
            }

            val choice = UserAuthUI.getInput("Choose an option to see the requested books\n0 to Exit\n") { it.toInt() }.takeIf { it!=0 }?:return
            if (choice > Admin.addBookRequests.size) {
                println(ENTER_VALID_OPTION)
                continue
            }

            AdminUtil.addRequestedBookToLibrary(choice-1)
            println("Book Successfully added")
        }
        println("Request Inbox Empty")
    }

    fun removeBook() {
        while (true) {
            val books = BookDataManager.getBooks()
            if(books.isEmpty()) {
                println("Library is empty")
                return
            }
            books.forEachIndexed { index, book ->  println("${index+1}.${book.bookName}")}
            val choice = UserAuthUI.getInput("Choose a book to remove\n0 to Exit\n") { it.toInt() }.takeIf { it!=0 }?:return
            if(choice>books.size){
                println(ENTER_VALID_OPTION)
                continue
            }

            if (AdminUtil.removeBookFromLibrary(choice,books)) println("Book removed successfully")
            else println("Someone already borrowed the book, so it can't be removed for now")
            break
        }
    }

    fun creditSalary() {
        val librarians = LibrarianDataManager.getLibrarians()
        if(librarians.isEmpty()){
            println(NO_LIBRARIANS_AVAILABLE)
            return
        }
        val totalSalaryCredited = AdminUtil.payLibrarians(librarians)
        if(totalSalaryCredited!=0) println("${totalSalaryCredited}rs paid in total")
        else println("Everyone has already been paid.")
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

        val index = UserAuthUI.getInput("") { it.toInt() }.takeIf { it != 0 && it <= librarians.size } ?: run {
            println("Option is Invalid")
            return
        }
        LibrarianDataManager.removeLibrarian(librarians[index-1])
        println("Librarian Fired")
    }

    fun printUserDetails() =
        println("Name: ${Admin.adminName}\nID: ${Admin.userID}\nPhone Number: ${Admin.phoneNumber}")
}