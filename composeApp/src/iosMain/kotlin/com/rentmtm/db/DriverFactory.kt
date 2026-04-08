package com.rentmtm.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        // A abordagem "Defensive" para o iOS Estático
        val configuration = DatabaseConfiguration(
            name = "rentmtm.db",
            version = AppDatabase.Schema.version.toInt(),
            create = { connection ->
                wrapConnection(connection) { AppDatabase.Schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) {
                    AppDatabase.Schema.migrate(it, oldVersion.toLong(), newVersion.toLong())
                }
            },
            // A SALVAÇÃO: Desliga a concorrência bloqueante que causa crash no iOS
            extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
        )

        return NativeSqliteDriver(configuration)
    }
}