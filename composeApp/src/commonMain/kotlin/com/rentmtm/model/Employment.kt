package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class Employment(
    var id: Int = 0,
    var employer: String? = null,
    var position: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var reasonForLeaving: String? = null,
    var responsibilities: String? = null
)