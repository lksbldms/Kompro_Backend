package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.dto.RatingRequest
import de.bazi.kompro_backend.entity.*
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

        // Berechtigungsprüfung
        val isAuthorized = session.creator.id == inspector.id ||
                session.inspectors.any { it.id == inspector.id }
        if (!isAuthorized) throw IllegalAccessException("Nicht autorisiert.")

        // Eindeutigkeitsprüfung
        if (repository.existsBySessionIdAndInspectorId(session.id!!, inspector.id!!)) {
            throw IllegalStateException("Rating existiert bereits.")
        }

        val rating = Rating(session = session, inspector = inspector)

        // Ergebnisse hinzufügen
        dto.results.forEach { resDto ->
            val item = itemRepository.findById(resDto.itemId).orElseThrow()
            val choice = choiceRepository.findById(resDto.ratingChoiceId).orElseThrow()
            rating.itemResults.add(ItemResult(rating, item, choice, resDto.note))
        }

        return repository.save(rating)
    }

    @Transactional
    fun update(id: UUID, dto: RatingRequest, requesterId: UUID): Rating {
        val rating = repository.findById(id).orElseThrow()
        if (rating.inspector.id != requesterId) throw IllegalAccessException("Nicht dein Rating.")

        // Alte Ergebnisse entfernen und neue hinzufügen (Orphan Removal erledigt den Rest)
        rating.itemResults.clear()
        dto.results.forEach { resDto ->
            val item = itemRepository.findById(resDto.itemId).orElseThrow()
            val choice = choiceRepository.findById(resDto.ratingChoiceId).orElseThrow()
            rating.itemResults.add(ItemResult(rating, item, choice, resDto.note))
        }

        return repository.save(rating)
    }

    @Transactional
    fun delete(id: UUID, requesterId: UUID) {
        val rating = repository.findById(id).orElseThrow()
        val isOwner = rating.inspector.id == requesterId
        val isSessionCreator = rating.session.creator.id == requesterId

        if (!isOwner && !isSessionCreator) throw IllegalAccessException("Keine Berechtigung.")
        repository.delete(rating)
    }
}