package org.example.dataManager

import org.example.entities.book.Book

object BookDataManager {
    private val listOfBooks = mutableListOf(
        Book("To Kill a Mockingbird", "Harper Lee", "Fiction"),
        Book("1984", "George Orwell", "Science Fiction"),
        Book("Pride and Prejudice", "Jane Austen", "Classic"),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction"),
        Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
    )

    fun getBooks() = listOfBooks.toList()

    fun setBook(book: Book) = listOfBooks.add(book)

    fun removeBook(book: Book) = listOfBooks.remove(book)
}