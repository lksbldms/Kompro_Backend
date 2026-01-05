package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.entity.AuthToken
import de.bazi.kompro_backend.service.AuthTokenService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/auth-tokens")
class AuthTokenController(private val service: AuthTokenService) {

    @GetMapping
    fun getAll() = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @PostMapping
    fun create(@RequestBody entity: AuthToken) = service.save(entity)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
