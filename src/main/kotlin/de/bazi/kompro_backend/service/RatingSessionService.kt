package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.dto.RatingSessionRequest
import de.bazi.kompro_backend.entity.RatingSession
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

    fun findById(id: UUID): RatingSession = repository.findById(id)
        .orElseThrow { NoSuchElementException("Session nicht gefunden") }

    @Transactional
    fun create(dto: RatingSessionRequest): RatingSession {
        val creator = userRepository.findById(dto.creatorId).orElseThrow()
        val examinee = userRepository.findById(dto.examineeId).orElseThrow()
        val area = areaRepository.findById(dto.competenceAreaId).orElseThrow()

        val session = RatingSession(creator, examinee, area, dto.status)
        dto.inspectorIds.forEach { id ->
            userRepository.findById(id).ifPresent { session.inspectors.add(it) }
        }
        return repository.save(session)
    }

    @Transactional
    fun delete(id: UUID, ownerId: UUID) {
        val session = repository.findById(id).orElseThrow()
        if (session.creator.id != ownerId) {
            throw IllegalAccessException("Nur der Creator darf diese Session löschen.")
        }
        repository.delete(session)
    }
}