package org.example.ui

import org.example.dataManager.BookRequestManager
import org.example.dataManager.MemberDataManager
import entities.users.Admin
import entities.users.Librarian
import org.example.util.*

object LibrarianUI {
     fun checkSalaryCredited(librarian: Librarian){
        if (librarian.isSalaryCredited) {
            println("Salary is credited!!!")
            librarian.isSalaryCredited = false
        }
    }

     private fun sendBookRequestToAdmin(book: String) {
        if (BooksUtil.checkDuplicateBooks(book.split(" ").first())) return
        Admin.addBookRequests.add(book)
        println("Request Sent to Admin")
    }

     fun checkAddBookRequests(){
        while (true) {
            val addBookRequests = BookRequestManager.getAddBookRequests()
            if(addBookRequests.isEmpty()){
                println("Inbox is empty")
                return
            }
            println("Choose an option to send request to admin\n0 to Exit")
            addBookRequests.forEachIndexed { index, book ->
                book.split(" ").let { println("${index + 1}.BookName=${it[0]}, AuthorName=${it[1]}, Category=${it[2]} by ${it[3]}") } }
            val choice = UserAuthUI.getInput("") { it.toInt() }.takeIf { it!=0 }?:break
            if (choice > addBookRequests.size) {
                println(ENTER_VALID_OPTION)
                continue
            }
            sendBookRequestToAdmin(addBookRequests[choice - 1])
            BookRequestManager.removeBookRequests(addBookRequests[choice - 1])
        }
    }

     fun checkBookRequests(){
        while (true) {
            var members = MemberDataManager.getMembers()
            if(members.isEmpty()) {
                println(NO_MEMBERS_AVAILABLE)
                return
            }
            members = members.filter { it.booksRequested.isNotEmpty() }
            if(members.isEmpty()){
                println("Requests is empty")
                break
            }

            members.filter { it.booksRequested.isNotEmpty() }.forEachIndexed { index, member ->  println("${index+1}.${member.name}")}
            val choice = UserAuthUI.getInput("Choose an option to see the requested books\n0 to Exit\n") { it.toInt() }
                .takeIf { it!=0 }?:break
            if(choice>members.size){
                println(ENTER_VALID_OPTION)
                continue
            }
            val memberBooksRequested = members[choice-1].booksRequested

            while (memberBooksRequested.isNotEmpty()) {
                memberBooksRequested.forEachIndexed { index, book -> println("${index + 1}.${book.bookName}") }
                val bookChoice = UserAuthUI.getInput("Choose an option to accept the book: ") { it.toInt() }
                if(bookChoice>memberBooksRequested.size){
                    println(ENTER_VALID_OPTION)
                    continue
                }
                println(memberBooksRequested[bookChoice-1].bookName+" book accepted")
                LibrarianUtil.addToBorrowedBooks(memberBooksRequested[bookChoice - 1], members[choice - 1])
            }
        }
    }

    fun banMember() {
        if(LibrarianUtil.banMembers()){
            println("Members who didn't receive any help were banned.")
            StaffUtil.displayMembers()
        }
        else println("No one is eligible to get banned.")
    }

    fun addBookToLibrary(librarianID: String, librarianName: String) {
        println("Enter Book Name")
        val bookName = readln()
        println("Enter Author Name")
        val authorName = readln()
        println("Enter Book Category")
        val category = readln()
        sendBookRequestToAdmin("$bookName $authorName $category by $librarianName ID: $librarianID")
    }

    fun printUserDetails(librarian: Librarian) =
        println("Name: ${librarian.name}\nUser ID: ${librarian.userID}\nPhone Number: ${librarian.phoneNumber}")
}