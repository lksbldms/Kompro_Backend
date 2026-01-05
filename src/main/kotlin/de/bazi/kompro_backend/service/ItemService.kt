package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.Item
import de.bazi.kompro_backend.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ItemService(private val repository: ItemRepository) {
    fun findAll(): List<Item> = repository.findAll()
    fun findById(id: UUID): Item? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: Item): Item = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
