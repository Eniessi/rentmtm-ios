package com.rentmtm.ui.request

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

@Composable
fun RequestServiceInfoScreen(
    service: ServiceOption,
    description: ServiceDescription,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabeçalho da profissão
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(service.imageRes),
                contentDescription = service.title,
                modifier = Modifier.size(88.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = service.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedCard(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                Text(
                    text = "Service Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description.summary,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                description.features.forEach { feature ->
                    Row(modifier = Modifier.padding(vertical = 6.dp)) {
                        Text("• ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(text = feature, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
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
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
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
                Text("Request", fontSize = 14.sp) // Redireciona para o Budget
                Spacer(Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
}