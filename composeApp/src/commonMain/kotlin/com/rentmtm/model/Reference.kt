package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class Reference(
    var id: Int = 0,
    var name: String? = null,
    var company: String? = null,
    var relationship: String? = null,
    var contactInfo: String? = null
)