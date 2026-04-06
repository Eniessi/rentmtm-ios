package com.rentmtm.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class BudgetStatus {
    PENDING,    // Aguardando resposta do profissional
    ACCEPTED,   // Profissional aceitou os termos
    REJECTED,   // Profissional recusou
    NEGOTIATING,// Contraproposta em andamento
    COMPLETED,  // Serviço prestado e pago
    CANCELLED,
    QUOTED// Cancelado por uma das partes
}