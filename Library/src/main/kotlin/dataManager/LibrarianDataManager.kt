package org.example.dataManager

import entities.users.Librarian

object LibrarianDataManager {

    private val listOfLibrarians: MutableList<Librarian> = mutableListOf(
        Librarian("priya", "Priya@123", "5551234567","7089708978097098"),
        Librarian("bob", "Bob@1234", "5555678901","6789697867986798"),
        Librarian("carol", "Carol@1234", "5558765432","4123412314234132"),
        Librarian("david", "David@1234", "5554321098","5867586756875678"),
    )

    fun getLibrarians() = listOfLibrarians.toList()

    fun removeLibrarian(librarian: Librarian) = listOfLibrarians.remove(librarian)
}