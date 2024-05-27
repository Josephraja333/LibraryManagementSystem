package org.example.util

import org.example.dataManager.BookDataManager
import org.example.dataManager.MemberDataManager
import org.example.entities.book.Book
import entities.users.Admin
import entities.users.Librarian
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object AdminUtil {
    fun addRequestedBookToLibrary(choice: Int) {
        Admin.addBookRequests[choice].split(" ")
            .let { BookDataManager.setBook(Book(it[0],it[1],it[2])) }
        Admin.addBookRequests.removeAt(choice)
    }

    fun removeBookFromLibrary(choice: Int,books: List<Book>): Boolean{
        if (MemberDataManager.getMembers()
            .flatMap { it.currentlyBorrowedBooks.map { borrowedBook -> borrowedBook.bookName } }
            .contains(books[choice-1].bookName)) {
            return false
        }
        BookDataManager.removeBook(books[choice-1])
        return true
    }

    fun payLibrarians(librarians: List<Librarian>): Int {
        var totalSalaryCredited = 0
        librarians.filter { ChronoUnit.DAYS.between(it.daysWorked, LocalDate.now()) > 30 }
            .forEach {
            totalSalaryCredited += 1000
            it.isSalaryCredited = true
            it.cardBalance += 1000
            it.daysWorked = LocalDate.now()
        }
        Admin.cardBalance -= totalSalaryCredited
        return totalSalaryCredited
    }
}