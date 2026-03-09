package com.rentmtm.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
    MALE,
    FEMALE,
    OTHER,
    PREFER_NOT_TO_SAY
}