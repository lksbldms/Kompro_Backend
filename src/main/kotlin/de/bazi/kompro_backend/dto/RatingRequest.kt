package de.bazi.kompro_backend.dto

import java.util.UUID

data class RatingRequest(
    val sessionId: UUID,
    val inspectorId: UUID,
    val itemId: UUID,
    val ratingChoiceId: UUID,
    val rawValue: Int,
    val note: String?
)
