package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
}
