package de.bazi.kompro_backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "item_ratings")
class ItemResult(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "rating_id")
    val rating: Rating,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id")
    val item: Item,

    @Column(name = "raw_value") var rawValue: Int,
    var note: String?
) : BaseEntity()