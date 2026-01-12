package de.bazi.kompro_backend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "item_results")
class ItemResult(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_id")
    val rating: Rating,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_choice_id")
    var choice: RatingChoice,

    var note: String? = null
) : BaseEntity()