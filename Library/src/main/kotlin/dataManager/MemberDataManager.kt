package org.example.dataManager

import entities.users.member.Member

object MemberDataManager {
    private val listOfMembers: MutableList<Member> = mutableListOf()

    fun setMember(member: Member) = listOfMembers.add(member)

    fun getMembers() = listOfMembers.toList()
}