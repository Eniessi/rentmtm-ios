package com.rentmtm.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import com.rentmtm.db.UserEntity

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()

    return AppDatabase(
        driver = driver,
        UserEntityAdapter = UserEntity.Adapter(
            addressIdAdapter = IntColumnAdapter
        )
    )
}