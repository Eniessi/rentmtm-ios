package com.rentmtm.model

import com.rentmtm.model.enums.PaymentMethodType
import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethod(
    var id: Long = 0L,         // payment_method_id
    var userId: Long,          // user_id (FK)
    var methodType: PaymentMethodType? = null,

    // Campos de Cartão (Opcionais no SQL)
    var cardHolder: String? = null,
    var cardNumber: String? = null,
    var cvv: String? = null,
    var expiryDate: String? = null,

    // Campo de PayPal (Opcional no SQL)
    var paypalEmail: String? = null
)