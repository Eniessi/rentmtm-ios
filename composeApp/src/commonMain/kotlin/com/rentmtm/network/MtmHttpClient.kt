package com.rentmtm.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object MtmHttpClient {
    // URL base do teu servidor (muda para o teu IP local ou domínio de produção)
    private const val BASE_URL = "http://10.0.2.2:8080/api"

    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true // Evita crashes se o backend mandar campos a mais
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("Ktor => $message") // Ajuda-te a ver o que está a acontecer no Logcat
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
        }
    }
}