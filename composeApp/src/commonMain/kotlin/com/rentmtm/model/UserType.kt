package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class UserType(
    var id: Int = 0,
    var description: String? = null,
    var profileDetails: String? = null
)