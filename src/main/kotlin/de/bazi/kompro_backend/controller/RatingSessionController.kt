package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.dto.RatingSessionRequest
import de.bazi.kompro_backend.service.RatingSessionService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/rating-sessions")
class RatingSessionController(private val service: RatingSessionService) {

    @GetMapping
    fun getAll() = service.findAll()

    @PostMapping
    fun create(@RequestBody dto: RatingSessionRequest) = service.create(dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID, @RequestParam ownerId: UUID) =
        service.delete(id, ownerId)
}