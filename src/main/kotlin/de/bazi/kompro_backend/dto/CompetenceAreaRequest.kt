package de.bazi.kompro_backend.dto

import java.util.UUID

data class CompetenceAreaRequest(
    val name: String,
    val description: String?,
    val ownerId: UUID,
    val competences: List<CompetenceDto> = emptyList()
)

data class CompetenceDto(
    val name: String,
    val items: List<ItemDto> = emptyList()
)

data class ItemDto(val name: String)
