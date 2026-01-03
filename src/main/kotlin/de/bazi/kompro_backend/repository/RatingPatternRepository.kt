package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.RatingPattern
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface RatingPatternRepository : JpaRepository<RatingPattern, UUID>
