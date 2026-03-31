package com.rentmtm.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.viewmodel.*

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            // Cabeçalho azul conforme modelo web enviado
            ChatHeader(state, onBack)
        },
        bottomBar = {
            ChatInput(state.inputText, viewModel::onInputChanged, viewModel::sendMessage)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.messages) { message ->
                MessageBubble(message)
            }
        }
    }
}

@Composable
private fun ChatHeader(state: ChatUiState, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0084FF)) // Azul do Lilo Web
            .statusBarsPadding()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
        }

        // Ícone do Profissional
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Text("${state.professionalName} • Professional", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(if (state.isOnline) "online" else "offline", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
    }
}

@Composable
private fun MessageBubble(message: ChatMessage) {
    val isMe = message.sender == MessageSender.ME
    val alignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
    val bgColor = if (isMe) Color(0xFF0084FF) else Color(0xFFEEF5FF)
    val textColor = if (isMe) Color.White else Color(0xFF011C3C)

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = alignment) {
        Column(horizontalAlignment = if (isMe) Alignment.End else Alignment.Start) {
            Surface(
                color = bgColor,
                shape = RoundedCornerShape(
                    topStart = 16.dp, topEnd = 16.dp,
                    bottomStart = if (isMe) 16.dp else 4.dp,
                    bottomEnd = if (isMe) 4.dp else 16.dp
                ),
                border = if (!isMe) borderStroke() else null
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(12.dp),
                    color = textColor,
                    fontSize = 15.sp
                )
            }
            Text(message.timestamp, fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
private fun ChatInput(text: String, onValueChange: (String) -> Unit, onSend: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 8.dp) {
        Row(
            modifier = Modifier.padding(16.dp).navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f).border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(24.dp)).padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                if (text.isEmpty()) Text("Type your message...", color = Color.Gray, fontSize = 14.sp)
                BasicTextField(value = text, onValueChange = onValueChange, modifier = Modifier.fillMaxWidth())
            }
            Spacer(Modifier.width(12.dp))
            IconButton(
                onClick = onSend,
                modifier = Modifier.size(48.dp).background(Color(0xFF0084FF), RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = Color.White)
            }
        }
    }
}

private fun borderStroke() = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD6E4F0))