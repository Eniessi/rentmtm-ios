package com.rentmtm.ui.request

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.*

data class ServiceCategoryItem(
    val name: String,
    val imageRes: DrawableResource,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestServiceSelectionScreen(
    onNavigateToBudget: (String) -> Unit,
    onBack: () -> Unit
) {
    // Reutilizando os recursos, mas adicionando uma descrição para o botão "INFO"
    val categories = remember {
        listOf(
            ServiceCategoryItem("Housekeeper", Res.drawable.housekeeper, "Cleaning and household organization services."),
            ServiceCategoryItem("Inspector", Res.drawable.inspector, "Property and structural condition inspections."),
            ServiceCategoryItem("Plumber", Res.drawable.plumber, "Installation and repair of piping and water systems."),
            ServiceCategoryItem("Electrician", Res.drawable.electrician, "Electrical installations, wiring, and repairs."),
            ServiceCategoryItem("Bricklayer", Res.drawable.bricklayer, "Construction, masonry, and concrete work."),
            ServiceCategoryItem("Mechanic", Res.drawable.mechanic, "Vehicle maintenance and mechanical repairs."),
            ServiceCategoryItem("Security Guard", Res.drawable.securityguard, "Protection and monitoring of properties or events."),
            ServiceCategoryItem("Driver", Res.drawable.driver, "Private transportation and delivery services."),
            ServiceCategoryItem("Other", Res.drawable.others, "Other unlisted professional services.")
        )
    }

    var selectedCategory by remember { mutableStateOf<ServiceCategoryItem?>(null) }
    var infoCategoryToShow by remember { mutableStateOf<ServiceCategoryItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Request Services", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(modifier = Modifier.padding(24.dp)) {
                    Button(
                        onClick = { selectedCategory?.let { onNavigateToBudget(it.name) } },
                        enabled = selectedCategory != null,
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Proceed to Budget", fontSize = 16.sp)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "What do you need?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Select a professional category to request a budget.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    ServiceCategoryCard(
                        category = category,
                        isSelected = selectedCategory == category,
                        onSelect = { selectedCategory = category },
                        onInfoClick = { infoCategoryToShow = category }
                    )
                }
            }
        }
    }

    // Modal de Informação (INFO)
    infoCategoryToShow?.let { category ->
        AlertDialog(
            onDismissRequest = { infoCategoryToShow = null },
            title = {
                Text(text = category.name, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            },
            text = {
                Text(text = category.description, fontSize = 16.sp)
            },
            confirmButton = {
                TextButton(onClick = { infoCategoryToShow = null }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun ServiceCategoryCard(
    category: ServiceCategoryItem,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onInfoClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface

    OutlinedCard(
        modifier = Modifier.aspectRatio(0.85f),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        colors = CardDefaults.outlinedCardColors(containerColor = containerColor),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { onSelect() }
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(category.imageRes),
                        contentDescription = category.name,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = category.name,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Surface(
                onClick = onInfoClick,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth().height(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "INFO",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }
            }
        }
    }
}