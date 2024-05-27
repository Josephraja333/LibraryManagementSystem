package org.example.entities.book

data class Book(
    val bookName: String,
    val author: String,
    val category: String,
    val bookRecord: BookRecord = BookRecord()
)  {
    override fun toString() =
        "Book = $bookName, Author = $author, Category = $category, Ratings = ${if (bookRecord.timesBorrowed == 0) "No ratings Yet" else bookRecord.ratings}"
}