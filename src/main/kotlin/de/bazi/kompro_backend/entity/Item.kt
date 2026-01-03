package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "items")
class Item(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "competence_id") val competence: Competence,
    var name: String
) : BaseEntity()
