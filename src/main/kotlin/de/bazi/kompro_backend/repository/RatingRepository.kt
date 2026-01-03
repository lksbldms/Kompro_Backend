package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.Rating
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface RatingRepository : JpaRepository<Rating, UUID> {
    fun findAllBySessionId(sessionId: UUID): List<Rating>
}
