package com.rentmtm.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.viewmodel.RegisterViewModel
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.*

@Composable
fun RegisterUserStep4Screen(
    viewModel: RegisterViewModel, // ⬅️ Conectado ao Cérebro
    profileTitle: String = "Tenant / Renter",
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // INDICADOR DE STEP
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.ic_step_04),
                contentDescription = "Step 4 Indicator",
                modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                contentScale = ContentScale.Fit
            )
        }

        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Identity and Security",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "How to Take Photos for Validation\nCome on, we're on the way! We need the photos as shown below",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // EXEMPLO FOTO DOCUMENTO
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Legible photo of the front and back of your identity card",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(Res.drawable.img_identity),
                contentDescription = "ID Example",
                modifier = Modifier.height(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // EXEMPLO FOTO SELFIE
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.img_selfie_id),
                contentDescription = "Selfie Example",
                modifier = Modifier.height(100.dp)
            )
            Text(
                text = "Front selfie photo holding the document below the face",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // CAIXA DE REGRAS
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Description",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Follow the instructions below to ensure your photo is accepted:",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                BulletPoint("Make sure the document is in good condition and free of obstructions.")
                BulletPoint("Take the photo in a well-lit place, avoiding shadows and reflections.")
                BulletPoint("Position the document on a flat surface and capture the entire visible area.")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // BOTÕES
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
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
                modifier = Modifier.width(130.dp).height(48.dp)
            ) {
                Text("Next")
                Spacer(Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun BulletPoint(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ==========================================
// PREVIEW
// ==========================================
@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun RegisterUserStep4ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserStep4Screen(
                viewModel = RegisterViewModel(),
                onBack = {},
                onNext = {}
            )
        }
    }
}