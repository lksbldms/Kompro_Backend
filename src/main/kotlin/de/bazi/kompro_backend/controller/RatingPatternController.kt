package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.entity.RatingPattern
import de.bazi.kompro_backend.service.RatingPatternService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/rating-patterns")
class RatingPatternController(private val service: RatingPatternService) {

    @GetMapping
    fun getAll() = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @PostMapping
    fun create(@RequestBody entity: RatingPattern) = service.save(entity)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
