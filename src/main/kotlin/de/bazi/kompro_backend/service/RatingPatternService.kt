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
    // Findet alle öffentlichen Muster ODER die eigenen
    fun findAllAvailable(userId: UUID): List<RatingPattern> {
        return repository.findAll().filter { it.isPublic || it.owner.id == userId }
    }

    fun findById(id: UUID): RatingPattern = repository.findById(id).orElseThrow {
        NoSuchElementException("RatingPattern nicht gefunden")
    }

    @Transactional
    fun create(dto: RatingPatternRequest): RatingPattern {
        val owner = userRepository.findById(dto.ownerId).orElseThrow()
        val pattern = RatingPattern(
            owner = owner,
            name = dto.name,
            description = dto.description,
            isPublic = dto.isPublic ?: false
        )
        dto.choices.forEach { cDto ->
            pattern.choices.add(RatingChoice(pattern = pattern, label = cDto.label, value = cDto.value))
        }
        return repository.save(pattern)
    }

    @Transactional
    fun update(id: UUID, dto: RatingPatternRequest): RatingPattern {
        val pattern = findById(id)

        // Validierung: Nur der Owner darf editieren
        if (pattern.owner.id != dto.ownerId) {
            throw IllegalAccessException("Nur der Besitzer kann dieses Muster bearbeiten")
        }

        pattern.name = dto.name
        pattern.description = dto.description
        pattern.isPublic = dto.isPublic ?: false

        // Choices aktualisieren (einfachster Weg: löschen und neu hinzufügen)
        pattern.choices.clear()
        dto.choices.forEach { cDto ->
            pattern.choices.add(RatingChoice(pattern = pattern, label = cDto.label, value = cDto.value))
        }

        return repository.save(pattern)
    }

    @Transactional
    fun deleteById(id: UUID, userId: UUID) {
        val pattern = findById(id)
        if (pattern.owner.id != userId) {
            throw IllegalAccessException("Nur der Besitzer kann dieses Muster löschen")
        }
        repository.delete(pattern)
    }
}