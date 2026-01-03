package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.AuthToken
import de.bazi.kompro_backend.entity.TokenType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface AuthTokenRepository : JpaRepository<AuthToken, UUID> {
    fun findByTokenValueAndTokenType(tokenValue: UUID, type: TokenType): AuthToken?
}
