package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.RatingPattern
import de.bazi.kompro_backend.repository.RatingPatternRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RatingPatternService(private val repository: RatingPatternRepository) {
    fun findAll(): List<RatingPattern> = repository.findAll()
    fun findById(id: UUID): RatingPattern? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: RatingPattern): RatingPattern = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
