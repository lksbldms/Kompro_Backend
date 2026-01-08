#!/bin/zsh

SERVICE_DIR="src/main/kotlin/de/bazi/kompro_backend/service"


# 1. CompetenceAreaService (Kaskadierendes Erstellen von Area -> Competence -> Item)
cat <<EOF > $SERVICE_DIR/CompetenceAreaService.kt
package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.*
import de.bazi.kompro_backend.dto.CompetenceAreaRequest
import de.bazi.kompro_backend.repository.CompetenceAreaRepository
import de.bazi.kompro_backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CompetenceAreaService(
    private val repository: CompetenceAreaRepository,
    private val userRepository: UserRepository
) {
    fun findAll(): List<CompetenceArea> = repository.findAll()
    fun findById(id: UUID): CompetenceArea? = repository.findById(id).orElse(null)

    @Transactional
    fun create(dto: CompetenceAreaRequest): CompetenceArea {
        val owner = userRepository.findById(dto.ownerId)
            .orElseThrow { RuntimeException("Owner not found") }
            
        val area = CompetenceArea(
            owner = owner,
            name = dto.name,
            description = dto.description
        )
        
        dto.competences.forEach { cDto ->
            val comp = Competence(area = area, name = cDto.name)
            cDto.items.forEach { iDto ->
                comp.items.add(Item(competence = comp, name = iDto.name))
            }
            area.competences.add(comp)
        }
        return repository.save(area)
    }

    fun deleteById(id: UUID) = repository.deleteById(id)
}
EOF

# 2. RatingPatternService
cat <<EOF > $SERVICE_DIR/RatingPatternService.kt
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
EOF

# 3. RatingSessionService
cat <<EOF > $SERVICE_DIR/RatingSessionService.kt
package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.*
import de.bazi.kompro_backend.dto.RatingSessionRequest
import de.bazi.kompro_backend.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RatingSessionService(
    private val repository: RatingSessionRepository,
    private val userRepository: UserRepository,
    private val areaRepository: CompetenceAreaRepository
) {
    fun findAll(): List<RatingSession> = repository.findAll()

    @Transactional
    fun create(dto: RatingSessionRequest): RatingSession {
        val creator = userRepository.findById(dto.creatorId).orElseThrow()
        val examinee = userRepository.findById(dto.examineeId).orElseThrow()
        val area = areaRepository.findById(dto.competenceAreaId).orElseThrow()
        
        val session = RatingSession(
            creator = creator,
            examinee = examinee,
            area = area,
            status = dto.status
        )
        
        dto.inspectorIds.forEach { id ->
            userRepository.findById(id).ifPresent { session.inspectors.add(it) }
        }
        
        return repository.save(session)
    }
}
EOF

# 4. RatingService
cat <<EOF > $SERVICE_DIR/RatingService.kt
package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.*
import de.bazi.kompro_backend.dto.RatingRequest
import de.bazi.kompro_backend.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RatingService(
    private val repository: RatingRepository,
    private val sessionRepository: RatingSessionRepository,
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository,
    private val choiceRepository: RatingChoiceRepository
) {
    @Transactional
    fun create(dto: RatingRequest): Rating {
        val session = sessionRepository.findById(dto.sessionId).orElseThrow()
        val inspector = userRepository.findById(dto.inspectorId).orElseThrow()
        val item = itemRepository.findById(dto.itemId).orElseThrow()
        val choice = choiceRepository.findById(dto.ratingChoiceId).orElseThrow()
        
        val rating = Rating(
            session = session,
            inspector = inspector,
            item = item,
            ratingChoice = choice,
            rawValue = dto.rawValue,
            note = dto.note
        )
        return repository.save(rating)
    }
}
EOF

echo "✅ Services wurden erfolgreich auf DTO-Verarbeitung umgeschrieben."