package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.RatingSession
import de.bazi.kompro_backend.repository.RatingSessionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RatingSessionService(private val repository: RatingSessionRepository) {
    fun findAll(): List<RatingSession> = repository.findAll()
    fun findById(id: UUID): RatingSession? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: RatingSession): RatingSession = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
