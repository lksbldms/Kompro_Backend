package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.Competence
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface CompetenceRepository : JpaRepository<Competence, UUID>
