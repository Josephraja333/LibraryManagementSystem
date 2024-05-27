package org.example.util

import org.example.dataManager.MemberDataManager
import entities.users.member.MemberStatus
import org.example.ui.UserAuthUI

object StaffUtil {
    fun getMemberDetails() {
        println()
        val members = MemberDataManager.getMembers()
        if(members.isEmpty()){
            println(NO_MEMBERS_AVAILABLE)
            return
        }
        println("Enter the corresponding number to look at the member's details\n0 to Exit")
        members.forEachIndexed { index, member -> println("${index + 1}.${member.name} ID:${member.userID}") }
        val index = UserAuthUI.getInput("") { it.toInt() }.takeIf { it != 0 } ?: return
        val member = members[index - 1]

        while (true) {
            println("Enter the corresponding number to look at the details\n0.Exit\n1.List of Borrowed Books\n2.History of Books Borrowed and Returned\n3.Get User Details")
            when (readln()) {
                "0" -> break

                "1" -> {
                    val borrowedBooks = member.currentlyBorrowedBooks
                    if (borrowedBooks.isEmpty()) println("No books have been borrowed")
                    else borrowedBooks.forEach { println(it) }
                }

                "2" -> {
                    val bookHistory = member.history
                    if (bookHistory.isEmpty()) println("No actions is taken")
                    else bookHistory.forEach { println(it) }
                }

                "3" -> member.printUserDetails()

                else -> println(ENTER_VALID_OPTION)
            }
        }
    }

    fun displayMembers() {
        val members = MemberDataManager.getMembers()
        if(members.isEmpty()){
            println(NO_MEMBERS_AVAILABLE)
            return
        }
        members.forEachIndexed { index, member ->
            when (member.memberStatus) {
                MemberStatus.BANNED -> println("${index + 1}.${member.name} ID:${member.userID} ${MemberStatus.BANNED}")
                MemberStatus.TEMPORARILY_REMOVED -> println("${index + 1}.${member.name} ID:${member.userID} ${MemberStatus.TEMPORARILY_REMOVED}")
                MemberStatus.AVAILABLE -> println("${index + 1}.${member.name} ID:${member.userID} ${MemberStatus.AVAILABLE}")
            }
        }
    }
}