package com.rentmtm.model

import com.rentmtm.model.enums.MovimentationType
import com.rentmtm.model.enums.MovimentationStatus
import kotlinx.serialization.Serializable

@Serializable
data class Movimentation(
    var id: Long = 0L,

    // Relações com os models anteriores
    var serviceOrderId: Long,     // A qual serviço esta grana pertence?
    var paymentMethodId: Long,    // Qual cartão/conta foi usado?

    // Valores (Sempre use Double ou BigDecimal para finanças)
    var grossAmount: Double,      // Valor total pago pelo cliente
    var feeAmount: Double = 0.0,  // Valor da taxa da plataforma
    var netAmount: Double,        // Valor limpo que o profissional recebe

    // Metadados da Transação
    var type: MovimentationType,
    var status: MovimentationStatus = MovimentationStatus.PENDING,
    var transactionCode: String? = null, // ID que vem do Stripe/PayPal/Gateway

    var createdAt: String? = null, // Data da criação
    var clearedAt: String? = null  // Data em que o dinheiro ficou disponível
)