package com.rentmtm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rentmtm.db.DriverFactory
import com.rentmtm.db.createDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val driverFactory = DriverFactory(this)
        val database = createDatabase(driverFactory)

        val users = database.userQueries.getAllUsers().executeAsList()

        println("TESTE DE BANCO: Foram encontrados ${users.size} usuários.")

        setContent {
            App()
        }
    }
}