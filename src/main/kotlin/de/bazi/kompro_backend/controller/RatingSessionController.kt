package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.entity.RatingSession
import de.bazi.kompro_backend.service.RatingSessionService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/rating-sessions")
class RatingSessionController(private val service: RatingSessionService) {

    @GetMapping
    fun getAll() = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @PostMapping
    fun create(@RequestBody entity: RatingSession) = service.save(entity)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
