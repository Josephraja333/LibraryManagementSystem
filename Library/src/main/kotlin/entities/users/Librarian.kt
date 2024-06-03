package entities.users

import org.example.util.LIBRARIAN_CARD_BALANCE
import org.example.util.LIBRARIAN_ID

data class Librarian(val librarianID: String): UserAccountDetails() {

    companion object{
        private var id = LIBRARIAN_ID

        val generatedUserId: String
            get() = "L${id++}"
    }

    constructor(
        name: String,
        password: String,
        phoneNumber: String,
        cardNumber: String,
    ) : this(generatedUserId) {
        this.name = name
        this.password = password
        this.phoneNumber = phoneNumber
        this.cardNumber = cardNumber
        this.cardBalance = LIBRARIAN_CARD_BALANCE
    }
}