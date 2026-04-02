package com.rentmtm.repository

import com.rentmtm.network.api.AuthApi
import com.rentmtm.network.dto.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class AuthRepository(private val authApi: AuthApi = AuthApi()) {

    suspend fun performLogin(email: String, passwordHash: String): Boolean {
        // Dispatchers.IO ou Dispatchers.Default, dependendo do suporte da sua versão de Coroutines
        return withContext(Dispatchers.Default) {
            val request = LoginRequest(email, passwordHash)
            val result = authApi.login(request)

            result.onSuccess { response ->
                println("Tech Lead Log -> Login Success! Token: ${response.token}")
                return@withContext true
            }.onFailure { error ->
                println("Tech Lead Log -> Login Failed: ${error.message}")
                return@withContext false
            }
            false
        }
    }
}