package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.entity.*
import de.bazi.kompro_backend.dto.CompetenceAreaRequest
import de.bazi.kompro_backend.repository.CompetenceAreaRepository
import de.bazi.kompro_backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CompetenceAreaService(
    private val repository: CompetenceAreaRepository,
    private val userRepository: UserRepository
) {
    fun findAll(): List<CompetenceArea> = repository.findAll()
    fun findById(id: UUID): CompetenceArea? = repository.findById(id).orElse(null)

    @Transactional
    fun create(dto: CompetenceAreaRequest): CompetenceArea {
        val owner = userRepository.findById(dto.ownerId)
            .orElseThrow { RuntimeException("Owner not found") }
            
        val area = CompetenceArea(
            owner = owner,
            name = dto.name,
            description = dto.description
        )
        
        dto.competences.forEach { cDto ->
            val comp = Competence(area = area, name = cDto.name)
            cDto.items.forEach { iDto ->
                comp.items.add(Item(competence = comp, name = iDto.name))
            }
            area.competences.add(comp)
        }
        return repository.save(area)
    }

    fun deleteById(id: UUID) = repository.deleteById(id)
}
