package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class CustomerFeedback(
    val id: Long = 0L,
    val serviceOrderId: Long,
    val customerId: Long,
    val professionalId: Long,
    val rating: Int, // Constraint: 1 to 5
    val comments: String? = null,
    val createdAt: String
)