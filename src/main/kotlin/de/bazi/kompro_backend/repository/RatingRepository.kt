package de.bazi.kompro_backend.repository

import de.bazi.kompro_backend.entity.Rating
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RatingRepository : JpaRepository<Rating, UUID> {
    fun existsBySessionIdAndInspectorId(sessionId: UUID, inspectorId: UUID): Boolean
    fun findBySessionIdAndInspectorId(sessionId: UUID, inspectorId: UUID): Rating?
}