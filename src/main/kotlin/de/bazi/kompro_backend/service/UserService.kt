package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.User
import de.bazi.kompro_backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(private val repository: UserRepository) {
    fun findAll(): List<User> = repository.findAll()
    fun findById(id: UUID): User? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: User): User = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
