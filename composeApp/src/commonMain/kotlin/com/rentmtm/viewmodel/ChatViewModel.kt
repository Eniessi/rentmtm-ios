package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Clock

enum class MessageSender { ME, PROFESSIONAL }

data class ChatMessage(
    val id: String,
    val text: String,
    val timestamp: String,
    val sender: MessageSender
)

data class ChatUiState(
    val professionalName: String = "Carlos Silva",
    val professionalTitle: String = "Master Electrician",
    val isOnline: Boolean = true,
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = ""
)

class ChatViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        // Dados mockados para simular o histórico inicial
        _uiState.update {
            it.copy(
                messages = listOf(
                    ChatMessage("1", "Hello! I saw your request for the kitchen pipe repair.", "10:00 AM", MessageSender.PROFESSIONAL),
                    ChatMessage("2", "I can be there tomorrow at 2 PM. Does that work for you?", "10:01 AM", MessageSender.PROFESSIONAL)
                )
            )
        }
    }

    fun onInputChanged(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        if (_uiState.value.inputText.isBlank()) return

        val newMessage = ChatMessage(
            id = Clock.System.toString(),
            text = _uiState.value.inputText,
            timestamp = "10:05 AM",
            sender = MessageSender.ME
        )

        _uiState.update { state ->
            state.copy(
                messages = state.messages + newMessage,
                inputText = ""
            )
        }
    }
}