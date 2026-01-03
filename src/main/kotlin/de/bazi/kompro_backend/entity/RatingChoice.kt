package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "rating_choices")
class RatingChoice(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "pattern_id") val pattern: RatingPattern,
    var label: String,
    var value: Int
) : BaseEntity()
