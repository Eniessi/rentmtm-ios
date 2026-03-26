package com.rentmtm.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class PaymentMethodType {
    CREDIT_CARD,
    DEBIT_CARD,
    PAYPAL
}