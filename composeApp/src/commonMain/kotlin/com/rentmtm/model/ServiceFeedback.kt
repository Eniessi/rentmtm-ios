package com.rentmtm.model

import com.rentmtm.model.enums.ReviewerType
import kotlinx.serialization.Serializable

@Serializable
data class ServiceFeedback(
    val id: Long = 0L,
    val serviceOrderId: Long,
    val reviewerId: Long,
    val targetUserId: Long,
    val reviewerType: ReviewerType,
    val rating: Int,
    val comments: String? = null,
    val createdAt: String
)