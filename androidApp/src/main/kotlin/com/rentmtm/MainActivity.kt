package com.rentmtm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.rentmtm.db.DriverFactory
import com.rentmtm.db.createDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var isAppReady = false
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        installSplashScreen()

        lifecycleScope.launch {
            delay(1500) // Congela por 1,5 segundos
            isAppReady = true // Libera a tela
        }

        // 3. A MÁGICA: O Android vai ficar perguntando a cada frame "Posso tirar a splash?"
        // Nós respondemos: "Só tire quando o isAppReady for true"
        splashScreen.setKeepOnScreenCondition {
            !isAppReady
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
        )

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