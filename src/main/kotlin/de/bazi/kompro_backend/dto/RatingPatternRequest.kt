package de.bazi.kompro_backend.dto

import java.util.UUID

data class RatingPatternRequest(
    val name: String,
    val description: String?,
    val ownerId: UUID,
    val choices: List<RatingChoiceDto>
)

data class RatingChoiceDto(
    val label: String,
    val value: Int
)
