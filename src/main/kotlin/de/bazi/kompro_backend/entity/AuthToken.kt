package de.bazi.kompro_backend.entity
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*
@Entity
@Table(name = "auth_tokens")
class AuthToken(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") val user: User,
    @Enumerated(EnumType.STRING) val tokenType: TokenType,
    @Column(unique = true, nullable = false) val tokenValue: UUID = UUID.randomUUID(),
    val expiryDate: OffsetDateTime
) : BaseEntity()

enum class TokenType { VERIFICATION, PASSWORD_RESET }
