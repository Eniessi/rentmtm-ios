package com.rentmtm.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.viewmodel.SupportRole
import com.rentmtm.viewmodel.SupportMessage
import com.rentmtm.viewmodel.SupportChatViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.logo_lilo_ai

@Composable
fun SupportChatScreen(
    viewModel: SupportChatViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Rola para o final automaticamente quando envia mensagem
    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            // Cabeçalho da Lilo
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFF0084FF)).statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                }

                // Avatar da Lilo AI
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.logo_lilo_ai),
                        contentDescription = "Lilo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(state.botName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(state.status, color = Color.White.copy(0.8f), fontSize = 12.sp)
                }
            }
        },
        bottomBar = {
            // Input padronizado
            Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier.padding(16.dp).navigationBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f).border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(24.dp)).padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        if (state.inputText.isEmpty()) Text("Type your message...", color = Color.Gray, fontSize = 14.sp)
                        BasicTextField(
                            value = state.inputText,
                            onValueChange = viewModel::onInputChanged,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Box(
                        modifier = Modifier.size(48.dp).background(Color(0xFF0084FF), RoundedCornerShape(12.dp)).clip(RoundedCornerShape(12.dp)).clickable {
                            viewModel.sendMessage()
                            coroutineScope.launch { if (state.messages.isNotEmpty()) listState.animateScrollToItem(state.messages.size) }
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, "Send", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
            }
        },
        containerColor = Color.White
    ) { p ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(p).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.messages) { msg ->
                val isMe = msg.sender == SupportRole.ME
                Box(Modifier.fillMaxWidth(), contentAlignment = if(isMe) Alignment.CenterEnd else Alignment.CenterStart) {
                    Surface(
                        color = if(isMe) Color(0xFF0084FF) else Color(0xFFEEF5FF),
                        shape = RoundedCornerShape(
                            topStart = 16.dp, topEnd = 16.dp,
                            bottomStart = if (isMe) 16.dp else 4.dp,
                            bottomEnd = if (isMe) 4.dp else 16.dp
                        ),
                        border = if (!isMe) borderStroke() else null
                    ) {
                        Text(msg.text, Modifier.padding(12.dp), color = if(isMe) Color.White else Color(0xFF011C3C), fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

private fun borderStroke() = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD6E4F0))