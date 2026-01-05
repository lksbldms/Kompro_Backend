package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.Rating
import de.bazi.kompro_backend.repository.RatingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RatingService(private val repository: RatingRepository) {
    fun findAll(): List<Rating> = repository.findAll()
    fun findById(id: UUID): Rating? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: Rating): Rating = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
