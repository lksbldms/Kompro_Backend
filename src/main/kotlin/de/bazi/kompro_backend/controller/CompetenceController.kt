package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.entity.Competence
import de.bazi.kompro_backend.service.CompetenceService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/competences")
class CompetenceController(private val service: CompetenceService) {

    @GetMapping
    fun getAll() = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
