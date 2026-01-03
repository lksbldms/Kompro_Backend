package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.RatingSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*
interface RatingSessionRepository : JpaRepository<RatingSession, UUID> {
    fun findAllByExamineeId(examineeId: UUID): List<RatingSession>
    @Query("SELECT rs FROM RatingSession rs JOIN rs.inspectors i WHERE i.id = :inspectorId")
    fun findAllByInspectorId(inspectorId: UUID): List<RatingSession>
    fun findByExamineeIdAndAreaId(examineeId: UUID, areaId: UUID): List<RatingSession>
}
