package com.rentmtm.ui.search

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.viewmodel.ProfessionalSearchUiState
import com.rentmtm.viewmodel.ProfessionalUiModel
import com.rentmtm.viewmodel.SearchProfessionalsViewModel

@Composable
fun FindProfessionalsScreen(
    viewModel: SearchProfessionalsViewModel,
    onBack: () -> Unit,
    onProfessionalSelected: (Long) -> Unit // Este é o nosso "Accept" que gera a Ordem de Serviço
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state) {
        if (state is ProfessionalSearchUiState.Idle) {
            viewModel.startSearchForService("Electrician")
        }
    }

    Scaffold(
        topBar = { /* Optional TopBar */ }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(targetState = state, label = "SearchStateTransition") { currentState ->
                when (currentState) {
                    is ProfessionalSearchUiState.Idle,
                    is ProfessionalSearchUiState.Searching -> {
                        SearchingView()
                    }
                    is ProfessionalSearchUiState.Success -> {
                        // CORREÇÃO: Repassando os 3 eventos necessários
                        ResultsView(
                            professionals = currentState.professionals,
                            onAccept = { professionalId ->
                                onProfessionalSelected(professionalId)
                            },
                            onDecline = { professionalId ->
                                // TODO: Chamar o ViewModel para remover este prof da lista
                                println("Tech Lead Log -> Cliente recusou o profissional $professionalId")
                            },
                            onAsk = { professionalId ->
                                // TODO: Navegar para tela de Chat
                                println("Tech Lead Log -> Cliente quer tirar dúvidas com o profissional $professionalId")
                            }
                        )
                    }
                    is ProfessionalSearchUiState.Empty -> {
                        EmptyResultView(onRetry = viewModel::resetSearch)
                    }
                    is ProfessionalSearchUiState.Error -> {
                        // Handle Error View
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Component 1: The Radar Animation View
// -------------------------------------------------------------

@Composable
private fun SearchingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RadarAnimation(modifier = Modifier.size(250.dp))
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Waiting for professionals...",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "We'll notify you as soon as a professional accepts your request.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            // CORREÇÃO: Encadear modificadores é a melhor prática no Compose
            modifier = Modifier.padding(horizontal = 40.dp).padding(top = 8.dp)
        )
    }
}

@Composable
private fun RadarAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "RadarTransition")

    // Wave 1
    val scale1 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Restart),
        label = "Wave1Scale"
    )
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Restart),
        label = "Wave1Alpha"
    )

    // Wave 2 (Delayed phase)
    val scale2 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing, delayMillis = 1000), RepeatMode.Restart),
        label = "Wave2Scale"
    )
    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing, delayMillis = 1000), RepeatMode.Restart),
        label = "Wave2Alpha"
    )

    val color = MaterialTheme.colorScheme.primary

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2f

            // Draw Wave 1
            drawCircle(
                color = color.copy(alpha = alpha1),
                radius = radius * scale1,
                style = Stroke(width = 4.dp.toPx())
            )
            // Draw Wave 2
            drawCircle(
                color = color.copy(alpha = alpha2),
                radius = radius * scale2,
                style = Stroke(width = 4.dp.toPx())
            )
            // Draw Center Pin/Avatar holder
            drawCircle(
                color = color,
                radius = 24.dp.toPx()
            )
        }
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}

// -------------------------------------------------------------
// Component 2: The Results View (Max 3 Cards)
// -------------------------------------------------------------
@Composable
private fun ResultsView(
    professionals: List<ProfessionalUiModel>,
    onAccept: (Long) -> Unit,
    onDecline: (Long) -> Unit,
    onAsk: (Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "We found ${professionals.size} professionals near you!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(24.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(professionals) { profile ->
                ProfessionalCard(
                    profile = profile,
                    onAccept = { onAccept(profile.id) },
                    onDecline = { onDecline(profile.id) },
                    onAsk = { onAsk(profile.id) }
                )
            }
        }
    }
}

@Composable
private fun ProfessionalCard(
    profile: ProfessionalUiModel,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onAsk: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar Placeholder
                Box(modifier = Modifier.size(50.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape), contentAlignment = Alignment.Center) {
                    Text(profile.name.take(1))
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = profile.name, fontWeight = FontWeight.Bold)
                    Text(text = "Quote: $${profile.quoteValue}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botões de Ação com padrão de cores solicitado
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // QUESTION (Azul Claro/Secundário)
                Button(
                    onClick = onAsk,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Ask a Question", fontSize = 12.sp)
                }

                // ACCEPT (Azul Escuro/Primário)
                Button(
                    onClick = onAccept,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Accept Quote", fontSize = 12.sp)
                }
            }

            // DECLINE (Vermelho/Error)
            TextButton(
                onClick = onDecline,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Decline Request", fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun EmptyResultView(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No professionals found nearby.", style = MaterialTheme.typography.titleMedium)
        Button(onClick = onRetry, modifier = Modifier.padding(top = 16.dp)) {
            Text("Expand Search Radius")
        }
    }
}

// ==========================================
// PREVIEWS
// ==========================================
@Preview
@Composable
fun PreviewSearchingView() {
    MaterialTheme {
        Surface {
            // Nota: Se a RadarAnimation depender de contexto complexo,
            // podes ter de a substituir por um Box simples no Preview.
            SearchingView()
        }
    }
}

@Preview
@Composable
fun PreviewResultsView() {
    MaterialTheme {
        Surface {
            val mockList = listOf(
                ProfessionalUiModel(1L, "John Doe", "Electrician", 2.5, 4.9, 150, 120.00),
                ProfessionalUiModel(2L, "Jane Smith", "Wiring Specialist", 5.1, 4.7, 89, 110.00)
            )

            ResultsView(
                professionals = mockList,
                onAccept = {},
                onDecline = {},
                onAsk = {}
            )
        }
    }
}