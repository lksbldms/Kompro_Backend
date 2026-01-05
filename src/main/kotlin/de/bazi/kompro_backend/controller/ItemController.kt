package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.entity.Item
import de.bazi.kompro_backend.service.ItemService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/items")
class ItemController(private val service: ItemService) {

    @GetMapping
    fun getAll() = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
