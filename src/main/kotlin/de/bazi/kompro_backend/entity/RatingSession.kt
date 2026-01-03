package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "rating_sessions")
class RatingSession(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "creator_id") val creator: User,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "examinee_id") val examinee: User,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "competence_area_id") val area: CompetenceArea,
    @Enumerated(EnumType.STRING) var status: SessionStatus = SessionStatus.OPEN,
    @ManyToMany
    @JoinTable(
        name = "session_inspectors",
        joinColumns = [JoinColumn(name = "session_id")],
        inverseJoinColumns = [JoinColumn(name = "inspector_id")]
    )
    val inspectors: MutableSet<User> = mutableSetOf()
) : BaseEntity()

enum class SessionStatus { OPEN, CLOSED }
