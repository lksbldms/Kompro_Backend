package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "ratings")
class Rating(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "session_id") val session: RatingSession,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "inspector_id") val inspector: User,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id") val item: Item,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "rating_choice_id") val ratingChoice: RatingChoice,
    @Column(name = "raw_value") var rawValue: Int,
    var note: String?
) : BaseEntity()
