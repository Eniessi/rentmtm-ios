package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    var id: Int = 0,
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var zipCode: String? = null
)
