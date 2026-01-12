package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "rating_patterns")
class RatingPattern(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner_id") val owner: User,
    var name: String,
    var description: String?,
    var isPublic: Boolean = false,
    @OneToMany(mappedBy = "pattern", cascade = [CascadeType.ALL]) val choices: MutableList<RatingChoice> = mutableListOf()
) : BaseEntity()
