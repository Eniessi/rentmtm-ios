package com.rentmtm.ui.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.model.ProfessionalActivity
import com.rentmtm.model.enums.ProfessionalType
import com.rentmtm.viewmodel.ActivityListUiState
import com.rentmtm.viewmodel.ProfessionalActivityListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalActivityListScreen(
    viewModel: ProfessionalActivityListViewModel,
    onBack: () -> Unit,
    onAddNew: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Activities", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNew, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, contentDescription = "Add New Activity", tint = Color.White)
            }
        }
    ) { padding ->
        ActivityListContent(
            state = state,
            modifier = Modifier.padding(padding),
            onTypeSelected = viewModel::onTypeSelected
        )
    }
}

@Composable
fun ActivityListContent(
    state: ActivityListUiState,
    modifier: Modifier = Modifier,
    onTypeSelected: (ProfessionalType?) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        // 1. SELEÇÃO DE CARD (TIPOS DE SERVIÇO)
        Text(
            text = "Filter by Category",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            items(state.availableTypes) { type ->
                val isSelected = state.selectedType == type
                FilterCard(
                    type = type,
                    isSelected = isSelected,
                    onClick = { onTypeSelected(type) }
                )
            }
        }

        // 2. LISTA DE ATIVIDADES
        if (state.activities.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No activities found for this category.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.activities) { activity ->
                    ActivityCard(activity)
                }
            }
        }
    }
}

@Composable
fun FilterCard(
    type: ProfessionalType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(80.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = type.name.lowercase().capitalize(),
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ActivityCard(activity: ProfessionalActivity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = activity.shortDescription,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = activity.createdAt,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = activity.detailedDescription,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview
    (showBackground = true)
@Composable
fun ProfessionalActivityListPreview() {
    MaterialTheme {
        ActivityListContent(
            state = ActivityListUiState(
                activities = listOf(
                    ProfessionalActivity(1, 0, ProfessionalType.PLUMBER, "Kitchen Leak", "Fixed a persistent leak in the main sink.", "2024-03-22")
                )
            ),
            onTypeSelected = {}
        )
    }
}