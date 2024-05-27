package org.example.dataManager

object BookRequestManager {
    private val bookRequestsToLibrarians =  mutableListOf<String>()

    fun setBookRequest(book: String) = bookRequestsToLibrarians.add(book)

    fun getAddBookRequests() = bookRequestsToLibrarians.toList()

    fun removeBookRequests(book: String) = bookRequestsToLibrarians.remove(book)
}