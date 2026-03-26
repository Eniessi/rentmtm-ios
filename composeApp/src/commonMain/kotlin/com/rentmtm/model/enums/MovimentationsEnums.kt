package com.rentmtm.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class MovimentationType {
    INFLOW,  // Entrada: Cliente pagando à plataforma
    OUTFLOW, // Saída: Plataforma pagando ao profissional
    FEE,     // Taxa: A parte que fica com o app
    REFUND   // Estorno: Devolução ao cliente
}

@Serializable
enum class MovimentationStatus {
    PENDING,   // Processando (ex: aguardando compensação do boleto/cartão)
    COMPLETED, // Dinheiro efetivamente liquidado
    FAILED,    // Falha na transação bancária
    BLOCKED    // Dinheiro retido por disputa na ServiceOrder
}