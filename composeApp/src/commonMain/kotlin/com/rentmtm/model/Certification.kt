package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class Certification(
    var id: Int = 0,
    var name: String? = null,
    var issuingOrganization: String? = null,
    var expirationDate: String? = null
)