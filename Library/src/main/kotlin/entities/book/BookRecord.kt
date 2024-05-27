package org.example.entities.book

data class BookRecord(
    var ratings: Float = 0f,
    var timesBorrowed: Int = 0,
    val reviews: MutableList<String> = mutableListOf()
)