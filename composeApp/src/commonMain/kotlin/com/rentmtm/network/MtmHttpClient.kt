package com.rentmtm.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// 1. Diga ao Kotlin: "Vou fornecer um HttpClient específico para cada plataforma"
expect fun createHttpClient(): HttpClient

// 2. A configuração comum (Negociação e Logs) que ambas as plataformas partilham
fun HttpClientConfig<*>.installMtmConfig() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("Ktor => $message")
            }
        }
        level = LogLevel.ALL
    }

    defaultRequest {
        // No iOS, 10.0.2.2 não existe (é o emulador do Android).
        // Se fores testar no telemóvel físico, precisas de colocar o IP local do teu PC (ex: 192.168.1.100).
        // Por agora, vou manter o teu, mas lembra-te disto!
        url("http://10.0.2.2:8080/api")
        contentType(ContentType.Application.Json)
    }
}

// 3. O Singleton que expõe o cliente de forma segura (Lazy)
object MtmHttpClient {
    // Usar 'lazy' garante que o iOS só tenta alocar memória quando tu efetivamente fizeres um pedido!
    val client: HttpClient by lazy {
        createHttpClient()
    }
}