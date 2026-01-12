package de.bazi.kompro_backend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "ratings")
class Rating(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    val session: RatingSession,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspector_id")
    val inspector: User,

    @OneToMany(mappedBy = "rating", cascade = [CascadeType.ALL], orphanRemoval = true)
    val itemResults: MutableList<ItemResult> = mutableListOf()
) : BaseEntity()