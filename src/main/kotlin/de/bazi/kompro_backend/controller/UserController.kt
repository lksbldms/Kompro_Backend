package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.entity.User
import de.bazi.kompro_backend.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {

    @GetMapping
    fun getAll() = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @PostMapping
    fun create(@RequestBody entity: User) = service.save(entity)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
