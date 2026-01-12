package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.dto.RatingRequest
import de.bazi.kompro_backend.service.RatingService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/ratings")
class RatingController(private val service: RatingService) {

    @PostMapping
    fun create(@RequestBody dto: RatingRequest) = service.create(dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID, @RequestParam requesterId: UUID) =
        service.delete(id, requesterId)
}