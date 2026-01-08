package de.bazi.kompro_backend.dto

import de.bazi.kompro_backend.entity.SessionStatus
import java.util.UUID

data class RatingSessionRequest(
    val creatorId: UUID,
    val examineeId: UUID,
    val competenceAreaId: UUID,
    val status: SessionStatus = SessionStatus.OPEN,
    val inspectorIds: Set<UUID> = emptySet()
)
