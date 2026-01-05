package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.Competence
import de.bazi.kompro_backend.repository.CompetenceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CompetenceService(private val repository: CompetenceRepository) {
    fun findAll(): List<Competence> = repository.findAll()
    fun findById(id: UUID): Competence? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: Competence): Competence = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
