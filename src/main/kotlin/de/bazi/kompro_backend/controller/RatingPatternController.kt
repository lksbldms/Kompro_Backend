package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.dto.RatingPatternRequest
import de.bazi.kompro_backend.entity.RatingPattern
import de.bazi.kompro_backend.service.RatingPatternService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/rating-patterns")
class RatingPatternController(private val service: RatingPatternService) {

    @GetMapping
    fun getAll(@RequestParam userId: UUID) = service.findAllAvailable(userId)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @PostMapping
    fun create(@RequestBody dto: RatingPatternRequest) = service.create(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody dto: RatingPatternRequest) =
        service.update(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID, @RequestParam userId: UUID) {
        service.deleteById(id, userId)
    }
}