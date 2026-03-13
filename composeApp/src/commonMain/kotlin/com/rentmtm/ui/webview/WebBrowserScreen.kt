package com.rentmtm.ui.webview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebBrowserScreen(
    url: String,        // O link que vai abrir
    pageTitle: String,  // O título que vai aparecer na barra superior
    onBack: () -> Unit  // Ação para voltar para a Home
) {
    // Inicializa o estado da WebView com a URL recebida
    val webViewState = rememberWebViewState(url)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = pageTitle,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        WebView(
            state = webViewState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}