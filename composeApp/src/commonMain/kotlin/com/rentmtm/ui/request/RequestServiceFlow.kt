package com.rentmtm.ui.request

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun RequestServiceFlow(
    onNavigateToBudget: (String) -> Unit,
    onBackToHome: () -> Unit
) {
    // Controla se estamos vendo a lista de profissões ou a info de uma específica
    val viewingInfoForState = remember { mutableStateOf<ServiceOption?>(null) }

    if (viewingInfoForState.value == null) {
        RequestServiceSelectionScreen(
            onNavigateToBudget = { selectedTitle ->
                onNavigateToBudget(selectedTitle)
            },
            onNavigateToInfo = { serviceTitle ->
                // Busca o objeto correspondente e muda o estado da tela
                val clickedService = serviceOptions.find { it.title == serviceTitle }
                viewingInfoForState.value = clickedService
            },
            onBack = onBackToHome
        )
    } else {
        val service = viewingInfoForState.value!!
        val description = serviceDescriptionsMap[service.title] ?: ServiceDescription(
            summary = "No details found.", features = emptyList()
        )

        RequestServiceInfoScreen(
            service = service,
            description = description,
            onBack = { viewingInfoForState.value = null }, // Volta para o Grid
            onNext = {
                // Se clicar em Request dentro do INFO, ele vai direto pro budget passando a profissão
                onNavigateToBudget(service.title)
            }
        )
    }
}