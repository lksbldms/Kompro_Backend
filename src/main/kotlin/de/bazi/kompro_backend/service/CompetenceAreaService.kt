package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.CompetenceArea
import de.bazi.kompro_backend.repository.CompetenceAreaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CompetenceAreaService(private val repository: CompetenceAreaRepository) {
    fun findAll(): List<CompetenceArea> = repository.findAll()
    fun findById(id: UUID): CompetenceArea? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: CompetenceArea): CompetenceArea = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
