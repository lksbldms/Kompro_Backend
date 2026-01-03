package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.CompetenceArea
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*
interface CompetenceAreaRepository : JpaRepository<CompetenceArea, UUID> {
    fun findAllByOwnerId(ownerId: UUID): List<CompetenceArea>
    @Query("SELECT ca FROM CompetenceArea ca JOIN SharedTemplate st ON st.area = ca WHERE st.sharedWith.id = :userId")
    fun findSharedWithUser(userId: UUID): List<CompetenceArea>
}
