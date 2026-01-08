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
) {
    @Transactional
    fun create(dto: RatingRequest): Rating {
        val session = sessionRepository.findById(dto.sessionId).orElseThrow()
        val inspector = userRepository.findById(dto.inspectorId).orElseThrow()
        val item = itemRepository.findById(dto.itemId).orElseThrow()

        val rating = Rating(
            session = session,
            inspector = inspector,
            item = item,
            rawValue = dto.rawValue,
            note = dto.note
        )
        return repository.save(rating)
    }
}
