package com.rentmtm.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.avatar_1
import rentmtm.composeapp.generated.resources.ic_step_01
@Composable
fun ProfileInfoScreen(
    profile: ProfileOption,
    description: ProfileDescription,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_step_01),
            contentDescription = "Step 1",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.align(Alignment.Start).fillMaxWidth(0.12f)
        )

        HorizontalDivider(color = Color.LightGray)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileAvatarHeader(profile)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedCard(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description.summary,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                description.features.forEach { feature ->
                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text("• ", fontWeight = FontWeight.Bold)
                        Text(text = feature, fontSize = 14.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.width(130.dp).height(48.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Back", fontSize = 14.sp)
            }

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.width(130.dp).height(48.dp)
            ) {
                Text("Next", fontSize = 14.sp)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun ProfileAvatarHeader(profile: ProfileOption) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(profile.imageRes),
            contentDescription = profile.title,
            modifier = Modifier.size(88.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = profile.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileInfoScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileInfoScreen(
                profile = ProfileOption(
                    title = "Tenant / Renter",
                    imageRes = rentmtm.composeapp.generated.resources.Res.drawable.avatar_1
                ),
                description = ProfileDescription(
                    summary = "This profile is intended for users looking for a rental that meets their needs, offering a fast and secure experience.",
                    features = listOf(
                        "Personalized search for items to rent.",
                        "View asset descriptions, photos and comments.",
                        "Request for reservations and rental contracts.",
                        "Complete payment management."
                    )
                ),
                onBack = {},
                onNext = {}
            )
        }
    }
}
