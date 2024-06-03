package entities.users

import org.example.util.*

object Admin : UserAccountDetails(ADMIN_NAME, ADMIN_PASSWORD, ADMIN_PHONE_NUMBER, ADMIN_CARD_NUMBER, ADMIN_CARD_BALANCE) {
    const val adminID = ADMIN_ID
}