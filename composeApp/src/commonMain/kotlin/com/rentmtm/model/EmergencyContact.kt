package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class EmergencyContact(
    var id: Int = 0,
    var name: String? = null,
    var relationship: String? = null,
    var phoneNumber: String? = null
)