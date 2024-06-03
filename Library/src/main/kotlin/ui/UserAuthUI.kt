package org.example.ui

import entities.users.Admin
import entities.users.Librarian
import org.example.dataManager.LibrarianDataManager
import org.example.dataManager.MemberDataManager
import entities.users.UserAccountDetails
import entities.users.member.Member
import entities.users.member.MemberStatus
import org.example.adminFunctions

object UserAuthUI {

     fun getUserNameInput(): String {
        println("Enter Name:")
        while (true) {
            val name = readln()
            if (Regex("^[a-zA-Z]+(\\s[a-zA-Z]+)*\$").matches(name) && name.length<=50) return name
            println("Enter a valid User Name (with no digits,symbols,maximum 50 characters)")
        }
    }

    fun getPasswordInput(): String {
        println("Enter Password:")
        while (true) {
            val password = readln()
            if (Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$").matches(password)) return password
            println("Enter a valid password (At least 8 characters with one upper,lower case,digit and symbol)")
        }
    }

    fun getPhoneNumberInput(): String {
        println("Enter Phone Number:")
        while (true) {
            val phoneNumber = readln()
            if (Regex("^\\d{10}$").matches(phoneNumber) && checkIfExist(phoneNumber)) return phoneNumber
            println("Enter a valid Phone Number")
        }
    }

    private fun checkIfExist(phoneNumber: String): Boolean {
        if (MemberDataManager.getMembers().any { it.phoneNumber == phoneNumber && it.memberStatus == MemberStatus.BANNED }) {
            println("This account is banned")
            return false
        } else if (MemberDataManager.getMembers().any { it.phoneNumber == phoneNumber }) {
            println("UserName/PhoneNumber is already in use, Try Something Different")
            return false
        }
        return true
    }

    fun createMember() {
        println()
        while (true) {
            val member = Member()
            MemberDataManager.setMember(member)
            break
        }
    }

    fun loginLibrarian(): Librarian? {
        val librarianList = LibrarianDataManager.getLibrarians().takeIf { it.isNotEmpty() } ?: return null
        var librarianID: String
        println("Enter Librarian ID")
        while (true){
            librarianID = readln().let { if (!it.contains("L")) "L$it" else it }
            if(librarianList.none { it.librarianID.equals(librarianID, true) }){
                println("Enter a valid ID")
                continue
            }
            break
        }

        println("Enter your password: (or) 0 If you forgot the password")
        var password = readln()
        if (password == "0") {
            if (forgotPassword(librarianList)) {
                password = getPasswordInput()
            } else {
                println("Phone number not found")
                return null
            }
        }

        val librarian = librarianList.find { it.librarianID.equals(librarianID,true) && it.password == password }
        if (librarian != null) return librarian
        else if(librarianList.find { it.librarianID.equals(librarianID,true) || it.password == password }!=null) println("Password Incorrect")
        else println("Account not found")
        return null
    }

    fun loginMember(): Member? {
        val membersList = MemberDataManager.getMembers()
        var memberID: String
        println("Enter Member ID")
        while (true){
            memberID = readln().let { if (!it.contains("M",true)) "M$it" else it }
            if(membersList.none { it.memberID.equals(memberID, true) }){
                println("Enter a valid ID")
                continue
            }
            break
        }

        if (MemberDataManager.getMembers().any { it.memberID == memberID && it.memberStatus == MemberStatus.BANNED }) {
            println("This account is banned")
            return null
        }

        else if (MemberDataManager.getMembers().any { it.memberID == memberID && it.memberStatus == MemberStatus.TEMPORARILY_REMOVED }) {
            println("This account is temporarily removed")
            return null
        }

        println("Enter your password: (or) 0 If you forgot the password")
        var password = readln()
        if (password == "0") {
            if (forgotPassword(membersList)) {
                password = getPasswordInput()
            } else {
                println("Phone number not found")
                return null
            }
        }

        val member = membersList.find { it.memberID.equals(memberID,true) && it.password == password }

        when {
            member == null && membersList.find { it.memberID.equals(memberID, true) || it.password == password } != null -> println("Password is incorrect")
            member == null -> println("Account not found")
            member.memberStatus == MemberStatus.AVAILABLE -> return member
            else -> println("Account temporarily removed")
        }

        return null
    }

    private fun forgotPassword(userAccountDetailsList: List<UserAccountDetails>): Boolean {
        val userAccountDetail = userAccountDetailsList.find { it.phoneNumber == getPhoneNumberInput() } ?: return false

        println("Set a new one!")
        userAccountDetail.password = getPasswordInput()
        println("New password set!")
        return true
    }

    fun loginAdmin(): Boolean {
        println("Enter Admin ID:")
        val adminID = readln().let { if (!it.contains("A",true)) "A$it" else it }
        println("Enter Password:")
        val password = readln()
        if (adminID.equals(Admin.adminID, true) && password == Admin.password) return true
        else println("Your Password is incorrect try again")
        return false
    }
}