package com.rentmtm.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image // Importação para imagens PNG!
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun ProfileSelectionScreen(onNextClicked: (String) -> Unit) {

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
                    onClick = { selectedProfile = profile }
                )
            }
        }

        Button(
            onClick = { selectedProfile?.let { onNextClicked(it.title) } },
            enabled = selectedProfile != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Next", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ProfileCard(profile: ProfileOption, isSelected: Boolean, onClick: () -> Unit) {

    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface

    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.aspectRatio(1f),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        colors = CardDefaults.outlinedCardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(profile.imageRes),
                contentDescription = profile.title,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = profile.title,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSelectionScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileSelectionScreen(onNextClicked = {})
        }
    }
}