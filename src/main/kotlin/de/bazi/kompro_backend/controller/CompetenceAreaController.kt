package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.entity.CompetenceArea
import de.bazi.kompro_backend.service.CompetenceAreaService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/competence-areas")
class CompetenceAreaController(private val service: CompetenceAreaService) {

    @GetMapping
    fun getAll() = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
