package com.rentmtm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSUserDefaults
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.setUnhandledExceptionHook

@OptIn(ExperimentalNativeApi::class)
fun MainViewController() = ComposeUIViewController {

    // 1. O HOOK DE EMERGÊNCIA: Apanha o erro um milissegundo antes de o iOS fechar a app
    setUnhandledExceptionHook { exception ->
        val prefs = NSUserDefaults.standardUserDefaults
        prefs.setObject(exception.stackTraceToString(), forKey = "LAST_CRASH_MTM")
        prefs.synchronize()
    }

    // 2. Lemos o disco rígido para ver se houve um crash anterior
    val prefs = NSUserDefaults.standardUserDefaults
    val lastCrash = prefs.stringForKey("LAST_CRASH_MTM")

    val showCrash = remember { mutableStateOf(lastCrash != null) }

    if (showCrash.value && lastCrash != null) {
        // 3. Se houve crash, paramos a aplicação e desenhamos o erro na ecrã!
        Box(modifier = Modifier.fillMaxSize().background(Color.Red).padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Button(onClick = {
                    prefs.removeObjectForKey("LAST_CRASH_MTM")
                    prefs.synchronize()
                    showCrash.value = false
                }) {
                    Text("Limpar Erro e Tentar Novamente")
                }
                Text(
                    text = "A CAIXA NEGRA (Kotlin Error):\n\n$lastCrash",
                    color = Color.White
                )
            }
        }
    } else {
        // 4. Se está tudo bem, corre a aplicação normal
        App()
    }
}