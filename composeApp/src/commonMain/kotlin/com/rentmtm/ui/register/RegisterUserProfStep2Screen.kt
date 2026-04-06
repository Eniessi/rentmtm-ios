package com.rentmtm.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.viewmodel.RegisterViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.bricklayer
import rentmtm.composeapp.generated.resources.driver
import rentmtm.composeapp.generated.resources.electrician
import rentmtm.composeapp.generated.resources.housekeeper
import rentmtm.composeapp.generated.resources.ic_step_02
import rentmtm.composeapp.generated.resources.inspector
import rentmtm.composeapp.generated.resources.mechanic
import rentmtm.composeapp.generated.resources.others
import rentmtm.composeapp.generated.resources.plumber
import rentmtm.composeapp.generated.resources.securityguard

data class OccupationItem(
    val name: String,
    val imageRes: DrawableResource
)

@Composable
fun RegisterUserProfStep2Screen(
    viewModel: RegisterViewModel,
    profileTitle: String = "Professional Allocated",
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    val occupations = listOf(
        OccupationItem("Housekeeper", Res.drawable.housekeeper),
        OccupationItem("Inspector", Res.drawable.inspector),
        OccupationItem("Plumber", Res.drawable.plumber),
        OccupationItem("Electrician", Res.drawable.electrician),
        OccupationItem("Bricklayer", Res.drawable.bricklayer),
        OccupationItem("Mechanic", Res.drawable.mechanic),
        OccupationItem("Security Guard", Res.drawable.securityguard),
        OccupationItem("Driver", Res.drawable.driver),
        OccupationItem("Other", Res.drawable.others)
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_step_02),
                    contentDescription = "Step 2 Indicator",
                    modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                    contentScale = ContentScale.Fit
                )
            }

            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "What's Your Occupation",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            occupations.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowItems.forEach { item ->
                        OccupationCard(
                            title = item.name,
                            imageRes = item.imageRes,
                            isSelected = viewModel.professionalInfo.selectedOccupation == item.name,
                            onClick = {
                                viewModel.professionalInfo = viewModel.professionalInfo.copy(selectedOccupation = item.name)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onBack,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.width(130.dp).height(48.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Back")
                }

                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.width(130.dp).height(48.dp),
                    enabled = viewModel.professionalInfo.selectedOccupation != null
                ) {
                    Text("Next")
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun OccupationCard(
    title: String,
    imageRes: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    val borderWidth = if (isSelected) 2.dp else 1.dp
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else Color.Transparent

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(borderWidth, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 24.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(imageRes),
            contentDescription = title,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun RegisterUserProfStep2ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserProfStep2Screen(
                viewModel = RegisterViewModel(),
                profileTitle = "Professional Allocated",
                onBack = {},
                onNext = {}
            )
        }
    }
}