package com.rentmtm.db

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.rentmtm.model.enums.Gender
import com.rentmtm.model.UserType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()

    // 1. O Adaptador para o seu Model complexo
    val customUserTypeAdapter = object : ColumnAdapter<UserType, String> {
        override fun decode(databaseValue: String): UserType {
            return Json.decodeFromString(databaseValue)
        }

        override fun encode(value: UserType): String {
            return Json.encodeToString(value)
        }
    }

    // 2. A Injeção de Dependência no Banco de Dados
    return AppDatabase(
        driver = driver,
        UserAdapter = User.Adapter(
            userTypeAdapter = customUserTypeAdapter,
            genderAdapter = EnumColumnAdapter(),
        ),
        PaymentMethodAdapter = PaymentMethod.Adapter(
            methodTypeAdapter = EnumColumnAdapter()
        ),
        ProfAllocatedProfileAdapter = ProfAllocatedProfile.Adapter(
            employmentTypeAdapter = EnumColumnAdapter(),
            workAuthorizationAdapter = EnumColumnAdapter(),
            salaryPeriodAdapter = EnumColumnAdapter(),
            educationLevelAdapter = EnumColumnAdapter(),
            workScheduleAdapter = EnumColumnAdapter()
        )
    )
}