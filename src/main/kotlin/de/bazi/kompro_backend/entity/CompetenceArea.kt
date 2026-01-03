package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "competence_areas")
class CompetenceArea(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner_id") val owner: User,
    var name: String,
    var description: String?,
    @OneToMany(mappedBy = "area", cascade = [CascadeType.ALL]) val competences: MutableList<Competence> = mutableListOf()
) : BaseEntity()
