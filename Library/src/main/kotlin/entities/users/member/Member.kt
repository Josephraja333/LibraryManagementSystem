package entities.users.member

import entities.users.UserAccountDetails
import org.example.entities.users.member.BorrowingInfo
import org.example.entities.users.member.HelpInfo
import org.example.entities.users.member.PaymentInfo
import org.example.util.MEMBER_ID

data class Member(val memberID: String = generatedUserId) : UserAccountDetails(memberID) {
    companion object {
        private var id = MEMBER_ID

        val generatedUserId: String
            get() = "M${id++}"
    }

    val borrowingInfo: BorrowingInfo = BorrowingInfo()
    val helpInfo: HelpInfo = HelpInfo()
    val paymentInfo: PaymentInfo = PaymentInfo()

    var memberStatus: MemberStatus = MemberStatus.AVAILABLE
}