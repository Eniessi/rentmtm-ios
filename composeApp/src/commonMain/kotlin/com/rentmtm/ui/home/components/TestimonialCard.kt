package com.rentmtm.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.model.TestimonialUiModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun TestimonialCard(
    testimonial: TestimonialUiModel,
    modifier: Modifier = Modifier
) {
    // Usamos um Box para sobrepor o Avatar na borda do Card Branco
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        // 1. O Fundo Branco
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp) // Empurra o card para baixo (metade do tamanho do avatar)
                .fillMaxHeight(), // Preenche a altura definida no carrossel
            shape = MaterialTheme.shapes.medium, // Bordas retas/levemente arredondadas como na web
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 56.dp, bottom = 24.dp), // Espaço extra no topo para o texto não bater na foto
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = testimonial.authorName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Usamos o role como o "Dallas, Texas" que está na imagem
                Text(
                    text = testimonial.authorRole,
                    color = MaterialTheme.colorScheme.primary, // Azul Padrão
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = testimonial.feedback,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    maxLines = 5,
                    lineHeight = 26.sp
                )
            }
        }

        // 2. O Avatar Flutuante
        Image(
            painter = painterResource(testimonial.avatarRes),
            contentDescription = "Avatar of ${testimonial.authorName}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
    }
}