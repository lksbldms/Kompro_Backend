package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "users")
class User(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "account_id") val account: Account,
    @Column(unique = true, nullable = false) var email: String,
    @Column(name = "password_hash", nullable = false) var passwordHash: String,
    var firstname: String?,
    var lastname: String?,
    @Column(name = "is_active") var isActive: Boolean = false
) : BaseEntity()
