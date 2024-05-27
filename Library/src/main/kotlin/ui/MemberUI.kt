package org.example.ui

import org.example.entities.book.Book
import org.example.dataManager.BookDataManager
import org.example.dataManager.BookRequestManager
import org.example.dataManager.MemberDataManager.getMembers
import entities.users.member.Member
import entities.users.member.MemberStatus
import org.example.util.BooksUtil
import org.example.util.DEFAULT_FINE_AMOUNT
import org.example.util.ENTER_VALID_OPTION
import org.example.util.MemberUtil
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object MemberUI {
    private fun getYesOrNoInput(): String {
        while (true) {
            println("Enter Y to accept (or) N to reject")
            return readln().lowercase().takeIf { it == "y" || it == "n" } ?: continue
        }
    }

    private fun getRatingsInput(): Float {
        while (true) {
            return UserAuthUI.getInput("Did you like the book rate it between 0 to 5.0: ") { it.toFloat() }
                .takeIf { it < 5.1 } ?: continue
        }
    }

    fun checkSubscriptionValidity(member: Member) {
        val daysDifference = ChronoUnit.DAYS.between(member.lastSubscriptionPaymentDate, LocalDate.now())
        if (daysDifference > 30) {
            println("Subscription period ended")
            if (MemberUtil.paySubscription(member)) println("Subscription amount of 300 is paid using ${member.cardNumber}")
            else temporarilyRemove(member)
        }
    }

    private fun temporarilyRemove(member: Member) {
        member.memberStatus = MemberStatus.TEMPORARILY_REMOVED
        println("Your card balance is in negative You're temporarily removed from this online library")

        val membersList = getMembers().filter { it != member }
        if (membersList.isEmpty()) {
            println("There is not enough members in the community, You're banned from this online library")
            return
        }

        membersList.forEachIndexed { it, eachMember -> println("${it + 1}.${eachMember.name}") }
        val userChoice = requestHelp().take(3)
        userChoice.forEach {
            member.helpRequested.add(membersList[it - 1].name)
            membersList[it - 1].helpAskedByOtherMembers.add("${member.name} ${member.currentFineAmount}rs")
        }
        println("Request sent, Your account will be banned if you didn't get help")
    }

    private fun sendBookRequestToLibrarians(book: String) {
        if (BooksUtil.checkDuplicateBooks(book.split(" ").first())) return
        BookRequestManager.setBookRequest(book)
        println("Request Sent to Librarians")
    }

    fun helpMember(member: Member) {
        while (member.helpAskedByOtherMembers.isNotEmpty()) {
            println("Other member's in the community is asking you to help them")
            println("Enter the corresponding number to check the person (or) 0 to Exit")
            member.helpAskedByOtherMembers.forEachIndexed { index, it -> println("${index + 1}.$it") }
            val choice = UserAuthUI.getInput("") { it.toInt() }.takeIf { it != 0 } ?: break

            if (choice > member.helpAskedByOtherMembers.size) {
                println(ENTER_VALID_OPTION)
                continue
            }

            val (personName, amountToBePaid) = member.helpAskedByOtherMembers[choice - 1].split(" ")
            val amountToPay = amountToBePaid.removeSuffix("rs").toInt()

            when (getYesOrNoInput()) {
                "y" -> {
                    if (MemberUtil.helpMember(member, personName, choice - 1, amountToPay)) {
                        println("Fine amount of ${amountToPay}rs Paid Using Card Number ${member.cardNumber}")
                        println("${member.cardBalance}rs is left in your card\nThanks for helping other members in the community")
                    } else println("You don't have enough money")
                }

                "n" -> member.helpAskedByOtherMembers.removeAt(choice - 1)
            }

            MemberUtil.removeHelpRequested(personName, member.name)
        }
    }

    fun searchBook(member: Member) {
        val books = BookDataManager.getBooks()
        if (books.isEmpty()) {
            println("Books are not available")
            return
        }
        val result = BooksUtil.searchBook()
        if (result.isEmpty()) {
            println("No matches found")
            return
        }
        result.forEachIndexed { index, book -> println("${index + 1}.$book") }
        println("Enter the corresponding number to select\n0 to Exit")
        val choice = UserAuthUI.getInput("") { it.toInt() }.takeIf { it != 0 && it <= books.size } ?: return
        requestBook(result.elementAt(choice - 1), member)
    }

    fun getBookFromLibrary(member: Member) {
        println()
        var books = BookDataManager.getBooks()
        if (books.isEmpty()) {
            println("Books are not available")
            return
        }
        var index: String
        while (true) {
            println("Enter the corresponding number to select\n0 to Exit\nF to Filter")
            books.forEachIndexed { count, book -> println("${count + 1}.$book") }
            index = readln()

            if (index.lowercase() == "f") {
                books = BooksUtil.filterBooks().takeIf { it.isNotEmpty() } ?: return
                println("Enter the corresponding number to select\n0 to Exit")
                books.forEachIndexed { count, book -> println("${count + 1}.$book") }
                index = UserAuthUI.getInput("") { it }
            } else if (!index.matches("\\d+".toRegex()) || index.length != 1) {
                println("Enter a valid option")
                continue
            }

            val choice = index.toInt().takeIf { it != 0 && it <= books.size } ?: return

            println("Enter 1 to get the book\nEnter 2 to see the reviews")
            when (readln()) {
                "1" -> break
                "2" -> {
                    books[choice - 1].bookRecord.reviews.forEach { println(it) }
                    println("Enter 1 to get the book\nEnter * to see other books")
                    if (readln() == "1") break
                }
            }
        }

        requestBook(books[index.toInt() - 1], member)
    }

    private fun requestBook(book: Book, member: Member) {
        if (book.bookName in member.currentlyBorrowedBooks.map { it.bookName }) {
            println("You have already borrowed this book")
            return
        } else if (book.bookName in member.booksRequested.map { it.bookName }) {
            println("Book already requested, kindly wait until a librarian accepts it")
            return
        }
        member.booksRequested.add(book)
        println("Book requested")
    }

    fun returnBook(member: Member) {
        println()
        val currentlyBorrowedBooks = member.currentlyBorrowedBooks
        if (currentlyBorrowedBooks.isEmpty()) {
            println("No books have been borrowed")
            return
        }
        println("Enter the corresponding number to select\n0 to Exit")
        currentlyBorrowedBooks.forEachIndexed { index, book -> println("${index + 1}.${book.bookName}") }

        val index = UserAuthUI.getInput("") { it.toInt() }
        if (index == 0 || index > currentlyBorrowedBooks.size) return
        val borrowedBook = currentlyBorrowedBooks.elementAt(index - 1)
        val books = BookDataManager.getBooks().takeIf { it.isNotEmpty() } ?: return
        val book = books[books.indexOfFirst { it.bookName == borrowedBook.bookName }]
        val timesBorrowed = currentlyBorrowedBooks.elementAt(index - 1).bookRecord.timesBorrowed

        if (member.currentFineAmount == 0) member.currentFineAmount = DEFAULT_FINE_AMOUNT

        book.bookRecord.ratings = if (timesBorrowed != 0) getRatingsInput() / timesBorrowed else getRatingsInput()
        print("Write a review of the book: ")
        book.bookRecord.reviews.add(member.name + ": " + readln())
        book.bookRecord.timesBorrowed++

        member.history.add(borrowedBook.bookName + " is returned")
        println(member.history.last())

        if (ChronoUnit.DAYS.between((member.booksBorrowedTime[book.bookName]), LocalDate.now()) > 30) {
            if (member.cardBalance - member.currentFineAmount < 0) {
                temporarilyRemove(member)
                return
            } else {
                member.cardBalance -= member.currentFineAmount
                println("Book borrowing time expired")
                println(
                    "Fine amount of ${member.currentFineAmount}rs Payed Using Card Number ${
                        member.cardNumber.replaceRange(
                            0 until 10,
                            "*".repeat(10)
                        )
                    }"
                )
                member.history.add(
                    "Fine amount of ${member.currentFineAmount}rs payed for " + member.currentlyBorrowedBooks.elementAt(
                        index - 1
                    ).bookName
                )
                member.currentFineAmount += member.currentFineAmount
                println("${member.cardBalance}rs is left in your card\nWarning: Next time the fine amount would be $member.currentFineAmount")
            }
        }
        member.booksBorrowedTime.remove(currentlyBorrowedBooks.elementAt(index - 1).bookName)
        currentlyBorrowedBooks.remove(member.currentlyBorrowedBooks.elementAt(index - 1))
    }

    private fun requestHelp(): MutableList<Int> {
        while (true) {
            println("Enter choice with space separated integers (max 3)")
            while (true) {
                try {
                    return readln().split(" ").map { it.toInt() }.toMutableList()
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
        }
    }

    fun requestNewBook(memberID: String, memberName: String) {
        println("Enter Book Name")
        val bookName = readln()
        println("Enter Author Name")
        val authorName = readln()
        println("Enter Book Category")
        val category = readln()
        sendBookRequestToLibrarians("$bookName $authorName $category by $memberName ID: $memberID")
    }

    fun printBorrowedBooks(member: Member) {
        if (member.currentlyBorrowedBooks.isEmpty()) println("No books have been borrowed")
        else member.currentlyBorrowedBooks.forEach { println(it) }
    }

    fun printHistory(member: Member) {
        val bookHistory = member.history
        if (bookHistory.isEmpty()) println("No actions is taken by the member")
        else bookHistory.forEach { println(it) }
    }

    fun printUserDetails(member: Member) =
        println("Name: ${member.name}\nUser ID: ${member.userID}\nPhone Number: ${member.phoneNumber}")
}