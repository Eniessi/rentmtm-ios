package com.rentmtm.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val passwordHash: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val userId: Long,
    val userType: String,
    val message: String
)