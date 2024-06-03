package org.example.entities.users.member

import org.example.util.DEFAULT_FINE_AMOUNT
import java.time.LocalDate

data class PaymentInfo(
    var lastSubscriptionPaymentDate: LocalDate = LocalDate.now(),
    var fineAmount: Int = DEFAULT_FINE_AMOUNT
)