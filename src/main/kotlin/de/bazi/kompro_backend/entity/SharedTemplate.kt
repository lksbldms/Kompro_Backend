package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "shared_templates")
class SharedTemplate(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "competence_area_id") val area: CompetenceArea,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "shared_with_user_id") val sharedWith: User,
    @Column(name = "can_edit") var canEdit: Boolean = false
) : BaseEntity()
