package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.AuthToken
import de.bazi.kompro_backend.repository.AuthTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AuthTokenService(private val repository: AuthTokenRepository) {
    fun findAll(): List<AuthToken> = repository.findAll()
    fun findById(id: UUID): AuthToken? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: AuthToken): AuthToken = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
