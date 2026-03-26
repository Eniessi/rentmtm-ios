package com.rentmtm.model

import com.rentmtm.model.enums.ServiceOrderStatus
import com.rentmtm.model.enums.PaymentStatus
import kotlinx.serialization.Serializable

@Serializable
data class ServiceOrder(
    var id: Long = 0L,

    // Rastreabilidade Absoluta
    var budgetId: Long,        // De qual orçamento esta OS nasceu?
    var customerId: Long,      // Quem paga
    var professionalId: Long,  // Quem executa

    // Dados do Serviço (Snapshot do que foi acordado no Budget)
    var finalTitle: String? = null,
    var finalDescription: String? = null,
    var finalValue: Double = 0.0,

    // Controle de Tempo Real (Uber de Serviço)
    var scheduledStartTime: String? = null, // Data/Hora combinada
    var actualStartTime: String? = null,    // Data/Hora do Check-in real
    var completionTime: String? = null,     // Data/Hora do Check-out

    // Localização do Serviço
    var serviceAddress: Address? = null,

    // Segurança e Confirmação
    var checkInCode: String? = null, // Código (ex: 4 dígitos) que o cliente passa para o profissional iniciar

    // Estados Financeiros e Operacionais
    var status: ServiceOrderStatus = ServiceOrderStatus.WAITING_START,
    var paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    var paymentMethodId: Long? = null, // Qual cartão/metodo será usado

    // Notas de Execução
    var professionalNotes: String? = null, // Relatório do que foi feito
    var customerFeedbackId: Long? = null   // Link para a avaliação futura
)