package com.rentmtm.network.api

import com.rentmtm.network.MtmHttpClient
import com.rentmtm.network.dto.LoginRequest
import com.rentmtm.network.dto.LoginResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthApi {
    private val client = MtmHttpClient.client

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = client.post("/api/auth/login") {
                setBody(request)
            }

            if (response.status.value in 200..299) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("Login failed with status: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}