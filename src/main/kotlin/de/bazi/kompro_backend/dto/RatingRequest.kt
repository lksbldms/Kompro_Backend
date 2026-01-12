package de.bazi.kompro_backend.dto

import ItemResultRequest
import java.util.UUID

data class RatingRequest(
    val sessionId: UUID,
    val inspectorId: UUID,
    val results: List<ItemResultRequest> = emptyList()
)