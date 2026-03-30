package com.rentmtm.model

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    var id: Long = 0L,
    var userId: Long, // Chave estrangeira ligando ao User
    var pushNotificationsEnabled: Boolean = true,
    val emailNotificationsEnabled: Boolean = true,
    var isDarkMode: Boolean = false,
    var language: String = "English",
    var twoFactorAuthEnabled: Boolean = false
)