package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.*
import de.bazi.kompro_backend.dto.RatingPatternRequest
import de.bazi.kompro_backend.repository.RatingPatternRepository
import de.bazi.kompro_backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RatingPatternService(
    private val repository: RatingPatternRepository,
    private val userRepository: UserRepository
) {
    fun findAll(): List<RatingPattern> = repository.findAll()

    @Transactional
    fun create(dto: RatingPatternRequest): RatingPattern {
        val owner = userRepository.findById(dto.ownerId).orElseThrow()
        val pattern = RatingPattern(
            owner = owner,
            name = dto.name,
            description = dto.description
        )
        dto.choices.forEach { cDto ->
            pattern.choices.add(RatingChoice(pattern = pattern, label = cDto.label, value = cDto.value))
        }
        return repository.save(pattern)
    }
}
