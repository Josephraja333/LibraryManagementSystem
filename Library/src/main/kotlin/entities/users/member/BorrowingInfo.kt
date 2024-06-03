package org.example.entities.users.member

import org.example.entities.book.Book
import java.time.LocalDate

data class BorrowingInfo(
    val currentlyBorrowedBooks: MutableList<Book> = mutableListOf(),
    val booksRequested: MutableList<Book> = mutableListOf(),
    val history: MutableList<String> = mutableListOf(),
    val booksBorrowedTime: MutableMap<String, LocalDate> = mutableMapOf(),
)
