package com.rentmtm.model

import org.jetbrains.compose.resources.DrawableResource

/**
 * Modelo de UI puramente desenhado para a camada de apresentação.
 * No futuro, isto pode ser facilmente mapeado a partir de uma resposta de API do backend.
 */
data class TestimonialUiModel(
    val id: Int,
    val authorName: String,
    val authorRole: String, // Ex: "Tenant", "Equipment Owner"
    val avatarRes: DrawableResource, // Usamos as imagens locais P2P
    val rating: Int, // 1 a 5 estrelas
    val feedback: String
)