package org.example.util

import org.example.entities.book.Book
import org.example.dataManager.BookDataManager

object BooksUtil {
    fun showAllBooks() = BookDataManager.getBooks().forEachIndexed { index, book -> println("${index + 1}.${book}")}

    fun searchBook(): List<Book> {
        val books = BookDataManager.getBooks().takeIf { it.isNotEmpty() } ?: return emptyList()

        println("Enter Book/Author Name")
        val search = readln()
        val result = books.filter { it.bookName.contains(search,true) || it.author.contains(search,true) }
                          .distinctBy { it.bookName }

        if (result.isEmpty()) println("No matches found")
        return result
    }

    fun filterBooks(): List<Book> {
        val books = BookDataManager.getBooks().takeIf { it.isNotEmpty() } ?: return emptyList()
        while (true) {
            println("Enter 1 to filter by author\nEnter 2 to filter by category\nEnter 3 to filter by ratings\n0 to Exit")
            when (readln()) {
                "1" -> {
                    println("Select an Author")
                    val authors = books.map { it.author }.toSet()
                    authors.forEachIndexed { index, author -> println("${index + 1}.$author") }
                    val choice = CommonUtil.getInput("") { it.toInt() }
                    val selectedAuthor = authors.elementAt(choice - 1).takeIf { choice != 0 && choice <= authors.size } ?: continue
                    return books.filter { it.author == selectedAuthor }
                }

                "2" -> {
                    println("Select an Category")
                    val category = books.map { it.category }.toSet()
                    category.forEachIndexed { index, it -> println("${index + 1}.$it") }
                    val choice = CommonUtil.getInput("") { it.toInt() }
                    val selectedCategory =
                        category.elementAt(choice - 1).takeIf { choice != 0 && choice <= category.size } ?: continue
                    return books.filter { it.category == selectedCategory }
                }

                "3" -> {
                    println("1.Low to High\n2.High to Low")
                    return when (readln()) {
                        "1" -> books.sortedBy { it.bookRecord.ratings }
                        "2" -> books.sortedByDescending { it.bookRecord.ratings }

                        else -> {
                            println("Enter 1 to filter by author\nEnter 2 to filter by category\n0 to Exit")
                            continue
                        }
                    }
                }

                "0" -> return emptyList()
                else -> println(ENTER_VALID_OPTION)
            }
        }
    }

    fun checkDuplicateBooks(bookName: String): Boolean {
        if (BookDataManager.getBooks().map { it.bookName.lowercase() }.contains(bookName.lowercase())) {
            println("Book is already available in the library")
            return true
        }
        return false
    }

    fun getBookDetails(): String {
        println("Enter Book Name")
        val bookName = generateSequence { readln().also { if(it.isEmpty()) println("Enter a valid book name") } }.first()
        println("Enter Author Name")
        val authorName = generateSequence { readln().also { if(it.isEmpty()) println("Enter a valid author name") } }.first()
        println("Enter Book Category")
        val category = generateSequence { readln().also { if(it.isEmpty()) println("Enter a valid category name") } }.first()
        return "$bookName $authorName $category"
    }

    fun printFilteredBooks() = filterBooks().forEach { println(it) }

    fun displayBooksBySearch() = searchBook().forEachIndexed { index, book -> println("${index + 1}.$book") }
}