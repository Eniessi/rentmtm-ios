package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class RentProfile(
    var id: Long = 0L,
    var description: String? = null,
    var profileDetails: String? = null
)