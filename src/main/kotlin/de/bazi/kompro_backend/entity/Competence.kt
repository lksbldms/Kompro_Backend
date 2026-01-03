package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "competences")
class Competence(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "area_id") val area: CompetenceArea,
    var name: String,
    @OneToMany(mappedBy = "competence", cascade = [CascadeType.ALL]) val items: MutableList<Item> = mutableListOf()
) : BaseEntity()
