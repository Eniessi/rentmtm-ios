package com.rentmtm.model

import com.rentmtm.model.enums.LanguageFluency
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    var id: Int = 0,
    var name: String? = null,
    var fluencyLevel: LanguageFluency? = null
)