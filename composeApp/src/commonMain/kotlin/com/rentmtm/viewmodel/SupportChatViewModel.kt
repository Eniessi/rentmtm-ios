package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Clock

enum class SupportRole { ME, LILO }

data class SupportMessage(
    val id: String,
    val text: String,
    val sender: SupportRole
)

data class SupportChatUiState(
    val botName: String = "Lilo • Assistant",
    val status: String = "online",
    val messages: List<SupportMessage> = emptyList(),
    val inputText: String = ""
)

class SupportChatViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SupportChatUiState())
    val uiState: StateFlow<SupportChatUiState> = _uiState.asStateFlow()

    init {
        // Mock inicial exatamente como na imagem enviada
        _uiState.update {
            it.copy(
                messages = listOf(
                    SupportMessage("1", "Welcome to Rent Month to Month! I'm here to make things easy.", SupportRole.LILO),
                    SupportMessage("2", "Wanna browse rentals, become an Owner/Landlord, or maybe even join our RentMTM team? Pick one!", SupportRole.LILO)
                )
            )
        }
    }

    fun onInputChanged(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        val currentText = _uiState.value.inputText
        if (currentText.isBlank()) return

        val newMsg = SupportMessage(
            id = Clock.System.toString(),
            text = currentText,
            sender = SupportRole.ME
        )

        _uiState.update { state ->
            state.copy(
                messages = state.messages + newMsg,
                inputText = ""
            )
        }

        // TODO: Aqui entraria a chamada para a API da IA da Lilo
        // simulando uma resposta:
        // simulateBotResponse()
    }
}