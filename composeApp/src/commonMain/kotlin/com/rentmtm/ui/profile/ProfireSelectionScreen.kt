package com.rentmtm.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image // Importação para imagens PNG!
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.*

data class ProfileOption(val title: String, val imageRes: DrawableResource)

val profileOptions = listOf(
    ProfileOption("Tenant / Renter", Res.drawable.avatar_1),
    ProfileOption("Owner / Landlord", Res.drawable.avatar_2),
    ProfileOption("Professional Allocated", Res.drawable.avatar_3),
    ProfileOption("Partner / Hotels", Res.drawable.avatar_4),
    ProfileOption("Buyer (MarketPlace)", Res.drawable.avatar_2),
    ProfileOption("Seller (MarketPlace)", Res.drawable.avatar_3)
)

@Composable
fun ProfileSelectionScreen(onNextClicked: (String) -> Unit, onNavigateToInfo: (String) -> Unit) {

    var selectedProfile by remember { mutableStateOf<ProfileOption?>(profileOptions[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Choose your profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Select the option that best describes you",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(profileOptions) { profile ->
                ProfileCard(
                    profile = profile,
                    isSelected = selectedProfile == profile,
                    onSelect = { selectedProfile = profile },
                    onInfoClick = { onNavigateToInfo(profile.title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = { selectedProfile?.let { onNextClicked(it.title) } },
            enabled = selectedProfile != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Next", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun ProfileCard(
    profile: ProfileOption,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onInfoClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface

    OutlinedCard(
        modifier = Modifier.aspectRatio(0.8f), // Um pouco mais alto para o botão
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
                        painter = painterResource(profile.imageRes),
                        contentDescription = profile.title,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = profile.title,
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
                modifier = Modifier.fillMaxWidth().height(40.dp)
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
@Preview(showBackground = true)
@Composable
fun ProfileSelectionScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileSelectionScreen(onNextClicked = {}, onNavigateToInfo = {})
        }
    }
}