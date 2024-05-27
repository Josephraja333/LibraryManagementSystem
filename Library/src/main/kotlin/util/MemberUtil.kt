package org.example.util

import org.example.dataManager.MemberDataManager.getMembers
import entities.users.Admin
import entities.users.member.Member
import entities.users.member.MemberStatus
import java.time.LocalDate

object MemberUtil {
    fun paySubscription(member: Member): Boolean {
        if(member.cardBalance-300 < 0) return false

        member.cardBalance-=300
        member.lastSubscriptionPaymentDate = LocalDate.now()
        Admin.subscriptionPaidBy.add(member.name)
        return true
    }

    fun helpMember(member: Member, personName: String, choice: Int, amountToPay: Int): Boolean {
        if(member.cardBalance - amountToPay < 0) return false

        removeBan(personName)
        member.cardBalance -= amountToPay
        member.helpAskedByOtherMembers.removeAt(choice)
        return true
    }

    private fun removeBan(memberName: String) =
        getMembers().find { it.name==memberName }
            ?.let { it.memberStatus = MemberStatus.AVAILABLE}

    fun removeHelpRequested(memberName: String, helpingMemberName: String) =
        getMembers().find { it.name==memberName }?.helpRequested?.remove(helpingMemberName)
}