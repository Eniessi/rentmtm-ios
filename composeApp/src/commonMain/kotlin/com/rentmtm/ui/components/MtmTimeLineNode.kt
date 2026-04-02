package com.rentmtm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.viewmodel.TimelineStep

@Composable
fun MtmTimelineNode(
    step: TimelineStep,
    isLastNode: Boolean
) {
    // Definir as cores com base no estado do passo
    val dotColor = when {
        step.isError -> MaterialTheme.colorScheme.error
        step.isCompleted || step.isCurrent -> MaterialTheme.colorScheme.primary
        else -> Color.LightGray
    }

    val lineColor = if (step.isCompleted) MaterialTheme.colorScheme.primary else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Garante que a linha vertical estique exatamente a altura do texto
    ) {
        // Coluna da Esquerda: O Ponto e a Linha
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 16.dp).fillMaxHeight()
        ) {
            // O Ponto / Ícone
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (step.isCompleted || step.isCurrent || step.isError) dotColor.copy(alpha = 0.2f) else Color.Transparent)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(dotColor)
                ) {
                    if (step.isCompleted && !step.isError) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.padding(2.dp))
                    } else if (step.isError) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.padding(2.dp))
                    } else if (step.isCurrent) {
                        // Um efeito visual para mostrar que está a carregar/ativo
                        Box(modifier = Modifier.fillMaxSize().background(dotColor, CircleShape))
                    }
                }
            }

            // A Linha Vertical de Conexão (não aparece no último item)
            if (!isLastNode) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f) // Estica para preencher o resto da altura da Row
                        .background(lineColor)
                )
            }
        }

        // Coluna da Direita: Os Textos
        Column(
            modifier = Modifier.padding(bottom = 32.dp) // Espaçamento entre os itens
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = step.title,
                    fontWeight = if (step.isCurrent) FontWeight.Bold else FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = if (step.isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
                if (step.timestamp != null) {
                    Text(
                        text = step.timestamp,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = step.description,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}