package com.rentmtm.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO // Baseado no seu build.gradle.kts

actual fun createHttpClient(): HttpClient {
    return HttpClient(CIO) {
        installMtmConfig()
    }
}