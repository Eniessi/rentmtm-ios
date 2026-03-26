package com.rentmtm.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ServiceOrderStatus {
    WAITING_START, // OS gerada, aguardando data/hora de início
    IN_PROGRESS,   // Profissional deu "Check-in" no local
    COMPLETED,     // Serviço finalizado e entregue
    CANCELED,      // Cancelada após a geração (pode gerar multa)
    DISPUTE        // Quando cliente ou profissional abre reclamação
}

@Serializable
enum class PaymentStatus {
    PENDING,
    AUTHORIZED, // Dinheiro cativo no cartão (escrow)
    PAID,       // Dinheiro liberado para o profissional
    REFUNDED    // Devolvido ao cliente
}