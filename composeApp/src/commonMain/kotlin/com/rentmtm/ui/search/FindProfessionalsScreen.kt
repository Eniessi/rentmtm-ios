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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rentmtm.viewmodel.ProfessionalSearchUiState
import com.rentmtm.viewmodel.ProfessionalUiModel
import com.rentmtm.viewmodel.SearchProfessionalsViewModel

@Composable
fun FindProfessionalsScreen(
    viewModel: SearchProfessionalsViewModel,
    onBack: () -> Unit,
    onProfessionalSelected: (Long) -> Unit // Navigates to send budget
) {
    val state by viewModel.uiState.collectAsState()

    // Kickoff the search automatically when the screen enters the Idle state
    LaunchedEffect(state) {
        if (state is ProfessionalSearchUiState.Idle) {
            viewModel.startSearchForService("Electrician")
        }
    }

    Scaffold(
        topBar = {
            // Optional TopBar
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Crossfade applies a smooth alpha transition between UI states
            Crossfade(targetState = state, label = "SearchStateTransition") { currentState ->
                when (currentState) {
                    is ProfessionalSearchUiState.Idle,
                    is ProfessionalSearchUiState.Searching -> {
                        SearchingView()
                    }
                    is ProfessionalSearchUiState.Success -> {
                        ResultsView(
                            professionals = currentState.professionals,
                            onSelect = onProfessionalSelected
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
            text = "Locating nearby professionals...",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "This might take a few seconds",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
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
    onSelect: (Long) -> Unit
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
                ProfessionalCard(profile = profile, onClick = { onSelect(profile.id) })
            }
        }
    }
}

@Composable
private fun ProfessionalCard(
    profile: ProfessionalUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Fake Avatar placeholder
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profile.name.take(1),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = profile.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(text = profile.professionTitle, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                    Text(text = " ${profile.rating} (${profile.totalJobs} jobs)", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(end = 8.dp))
                    Text(text = "• ${profile.distanceKm} km away", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
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
@Preview(showBackground = true)
@Composable
fun PreviewSearchingState() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            SearchingView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuccessState() {
    val mockData = listOf(
        ProfessionalUiModel(1, "Carlos Silva", "Master Electrician", 1.2, 4.9, 142),
        ProfessionalUiModel(2, "Ana Souza", "Residential Electrician", 3.5, 4.7, 89),
        ProfessionalUiModel(3, "Marcos Paulo", "Wiring Specialist", 5.0, 4.8, 210)
    )
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            ResultsView(professionals = mockData, onSelect = {})
        }
    }
}