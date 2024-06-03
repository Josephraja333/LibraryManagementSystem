package org.example.ui

import org.example.dataManager.BookRequestManager
import org.example.dataManager.MemberDataManager
import entities.users.Librarian
import entities.users.member.MemberStatus
import org.example.dataManager.BookDataManager
import org.example.entities.book.Book
import org.example.util.*
import java.time.LocalDate

object LibrarianUI {
     fun checkAddBookRequests(){
        while (true) {
            val addBookRequests = BookRequestManager.getAddBookRequests()
            if(addBookRequests.isEmpty()){
                println("Inbox is empty")
                return
            }
            println("Choose an option to add the book to library\n0 to Exit")
            addBookRequests.forEachIndexed { index, book ->
                book.split(" ").let { println("${index + 1}.BookName=${it[0]}, AuthorName=${it[1]}, Category=${it[2]} ${it[3]} ${it[4]} ${it[5]} ${it[6]}") } }
            val choice = CommonUtil.getInput("") { it.toInt() }.takeIf { it!=0 }?:break
            if (choice > addBookRequests.size) {
                println(ENTER_VALID_OPTION)
                continue
            }
            addRequestedBookToLibrary(addBookRequests[choice-1])
            BookRequestManager.removeBookRequests(addBookRequests[choice - 1])
        }
    }

    private fun addRequestedBookToLibrary(book: String) {
        if (BooksUtil.checkDuplicateBooks(book.split(" ").first())) return
        book.split(" ")
            .let { BookDataManager.setBook(Book(it[0],it[1],it[2])) }
        println("Book Successfully added")
    }

     fun checkBookRequests(){
        while (true) {
            var members = MemberDataManager.getMembers()
            if(members.isEmpty()) {
                println(NO_MEMBERS_AVAILABLE)
                return
            }
            members = members.filter { it.borrowingInfo.booksRequested.isNotEmpty() }
            if(members.isEmpty()){
                println("Requests is empty")
                break
            }

            members.filter { it.borrowingInfo.booksRequested.isNotEmpty() }.forEachIndexed { index, member ->  println("${index+1}.${member.name}")}
            val choice = CommonUtil.getInput("Choose an option to see the requested books\n0 to Exit\n") { it.toInt() }
                .takeIf { it!=0 }?:break
            if(choice>members.size){
                println(ENTER_VALID_OPTION)
                continue
            }
            val memberBooksRequested = members[choice-1].borrowingInfo.booksRequested

            while (memberBooksRequested.isNotEmpty()) {
                memberBooksRequested.forEachIndexed { index, book -> println("${index + 1}.${book.bookName}") }
                val bookChoice = CommonUtil.getInput("Choose an option to accept the book: ") { it.toInt() }
                if(bookChoice>memberBooksRequested.size){
                    println(ENTER_VALID_OPTION)
                    continue
                }
                println(memberBooksRequested[bookChoice-1].bookName+" book accepted")
                val member = members[choice - 1]
                val book = memberBooksRequested[bookChoice - 1]

                member.borrowingInfo.booksRequested.remove(book)
                member.borrowingInfo.currentlyBorrowedBooks.add(book.copy())
                member.borrowingInfo.booksBorrowedTime[book.bookName] = LocalDate.now()
                member.borrowingInfo.history.add(book.bookName + " is borrowed")
            }
        }
    }

    fun banMember() {
        val members = MemberDataManager.getMembers()
            .filter { it.memberStatus == MemberStatus.TEMPORARILY_REMOVED && it.helpInfo.helpRequested.isEmpty() }
            .onEach { it.memberStatus = MemberStatus.BANNED }
            .isNotEmpty()

        if(members){
            println("Members who didn't receive any help were banned.")
            StaffUtil.displayMembers()
        }
        else println("No one is eligible to get banned.")
    }

    fun printUserDetails(librarian: Librarian) =
        println("Name: ${librarian.name}\nUser ID: ${librarian.librarianID}\nPhone Number: ${librarian.phoneNumber}")

    fun showCardBalance(librarian: Librarian) = librarian.showCardBalance()

    fun settings(librarian: Librarian) = librarian.settings()

    fun showAllBooks() = BooksUtil.showAllBooks()

    fun searchBooks() = BooksUtil.displayBooksBySearch()

    fun printFilteredBooks() = BooksUtil.printFilteredBooks()

    fun displayMembers() = StaffUtil.displayMembers()

    fun addBookToLibrary() = addRequestedBookToLibrary(BooksUtil.getBookDetails())

    fun showMemberDetails() = StaffUtil.getMemberDetails()
}