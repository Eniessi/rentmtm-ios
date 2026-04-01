package com.rentmtm.model

import com.rentmtm.model.enums.ProfessionalType
import kotlinx.serialization.Serializable

@Serializable
data class ProfessionalActivity(
    val id: Long = 0L,
    val professionalId: Long,
    val professionalType: ProfessionalType,
    val shortDescription: String,
    val detailedDescription: String,
    val createdAt: String
)