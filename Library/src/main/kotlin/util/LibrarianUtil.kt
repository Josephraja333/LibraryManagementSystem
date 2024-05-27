package org.example.util

import org.example.dataManager.MemberDataManager
import org.example.entities.book.Book
import entities.users.member.Member
import entities.users.member.MemberStatus
import java.time.LocalDate

object LibrarianUtil {
    fun addToBorrowedBooks(book: Book, member: Member) {
        println()
        member.booksRequested.remove(book)
        member.currentlyBorrowedBooks.add(book.copy())
        member.booksBorrowedTime[book.bookName] = LocalDate.now()
        member.history.add(book.bookName + " is borrowed")
    }

    fun banMembers(): Boolean {
        var members = MemberDataManager.getMembers()
        members = members.filter { it.memberStatus == MemberStatus.TEMPORARILY_REMOVED && it.helpRequested.isEmpty() }
            .onEach { it.memberStatus = MemberStatus.BANNED }

        return members.isNotEmpty()
    }
}