package com.rentmtm.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class LanguageFluency {
    BASIC,
    INTERMEDIATE,
    ADVANCED,
    FLUENT,
    NATIVE
}