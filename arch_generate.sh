#!/bin/zsh

# Ordnerstruktur anlegen
mkdir -p src/main/kotlin/de/bazi/kompro_backend/{config,controller,dto,entity,repository,service,security,util}
mkdir -p src/main/resources/db/migration

# Pfade basierend auf deiner neuen Struktur
BASE_PATH="src/main/kotlin/de/bazi/kompro_backend"
ENTITY_PATH="$BASE_PATH/entity"
REPO_PATH="$BASE_PATH/repository"

# --- ENTITIES ---

cat <<EOF > "$ENTITY_PATH/BaseEntity.kt"
package de.bazi.kompro_backend.entity
import jakarta.persistence.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime
import java.util.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @Id @GeneratedValue @Column(columnDefinition = "UUID") val id: UUID? = null,
    @Version val version: Long = 0,
    @CreatedDate @Column(name = "created_at", updatable = false) var createdAt: OffsetDateTime? = null,
    @LastModifiedDate @Column(name = "updated_at") var updatedAt: OffsetDateTime? = null
)
EOF

cat <<EOF > "$ENTITY_PATH/Account.kt"
package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "accounts")
class Account : BaseEntity()
EOF

cat <<EOF > "$ENTITY_PATH/User.kt"
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
EOF

cat <<EOF > "$ENTITY_PATH/AuthToken.kt"
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
EOF

cat <<EOF > "$ENTITY_PATH/RatingPattern.kt"
package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "rating_patterns")
class RatingPattern(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner_id") val owner: User,
    var name: String,
    var description: String?,
    @OneToMany(mappedBy = "pattern", cascade = [CascadeType.ALL]) val choices: MutableList<RatingChoice> = mutableListOf()
) : BaseEntity()
EOF

cat <<EOF > "$ENTITY_PATH/RatingChoice.kt"
package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "rating_choices")
class RatingChoice(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "pattern_id") val pattern: RatingPattern,
    var label: String,
    var value: Int
) : BaseEntity()
EOF

cat <<EOF > "$ENTITY_PATH/CompetenceArea.kt"
package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "competence_areas")
class CompetenceArea(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner_id") val owner: User,
    var name: String,
    var description: String?,
    @OneToMany(mappedBy = "area", cascade = [CascadeType.ALL]) val competences: MutableList<Competence> = mutableListOf()
) : BaseEntity()
EOF

cat <<EOF > "$ENTITY_PATH/SharedTemplate.kt"
package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "shared_templates")
class SharedTemplate(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "competence_area_id") val area: CompetenceArea,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "shared_with_user_id") val sharedWith: User,
    @Column(name = "can_edit") var canEdit: Boolean = false
) : BaseEntity()
EOF

cat <<EOF > "$ENTITY_PATH/Competence.kt"
package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "competences")
class Competence(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "area_id") val area: CompetenceArea,
    var name: String,
    @OneToMany(mappedBy = "competence", cascade = [CascadeType.ALL]) val items: MutableList<Item> = mutableListOf()
) : BaseEntity()
EOF

cat <<EOF > "$ENTITY_PATH/Item.kt"
package de.bazi.kompro_backend.entity
import jakarta.persistence.*
@Entity
@Table(name = "items")
class Item(
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "competence_id") val competence: Competence,
    var name: String
) : BaseEntity()
EOF

cat <<EOF > "$ENTITY_PATH/RatingSession.kt"
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
EOF

cat <<EOF > "$ENTITY_PATH/Rating.kt"
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
EOF

# --- REPOSITORIES ---

cat <<EOF > "$REPO_PATH/UserRepository.kt"
package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
}
EOF

cat <<EOF > "$REPO_PATH/CompetenceAreaRepository.kt"
package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.CompetenceArea
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*
interface CompetenceAreaRepository : JpaRepository<CompetenceArea, UUID> {
    fun findAllByOwnerId(ownerId: UUID): List<CompetenceArea>
    @Query("SELECT ca FROM CompetenceArea ca JOIN SharedTemplate st ON st.area = ca WHERE st.sharedWith.id = :userId")
    fun findSharedWithUser(userId: UUID): List<CompetenceArea>
}
EOF

cat <<EOF > "$REPO_PATH/RatingSessionRepository.kt"
package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.RatingSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*
interface RatingSessionRepository : JpaRepository<RatingSession, UUID> {
    fun findAllByExamineeId(examineeId: UUID): List<RatingSession>
    @Query("SELECT rs FROM RatingSession rs JOIN rs.inspectors i WHERE i.id = :inspectorId")
    fun findAllByInspectorId(inspectorId: UUID): List<RatingSession>
    fun findByExamineeIdAndAreaId(examineeId: UUID, areaId: UUID): List<RatingSession>
}
EOF

cat <<EOF > "$REPO_PATH/RatingRepository.kt"
package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.Rating
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface RatingRepository : JpaRepository<Rating, UUID> {
    fun findAllBySessionId(sessionId: UUID): List<Rating>
}
EOF

cat <<EOF > "$REPO_PATH/AuthTokenRepository.kt"
package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.AuthToken
import de.bazi.kompro_backend.entity.TokenType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface AuthTokenRepository : JpaRepository<AuthToken, UUID> {
    fun findByTokenValueAndTokenType(tokenValue: UUID, type: TokenType): AuthToken?
}
EOF

# Zusätzliche Repositories
cat <<EOF > "$REPO_PATH/CompetenceRepository.kt"
package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.Competence
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface CompetenceRepository : JpaRepository<Competence, UUID>
EOF

cat <<EOF > "$REPO_PATH/ItemRepository.kt"
package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface ItemRepository : JpaRepository<Item, UUID>
EOF

cat <<EOF > "$REPO_PATH/RatingPatternRepository.kt"
package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.RatingPattern
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface RatingPatternRepository : JpaRepository<RatingPattern, UUID>
EOF

echo "✅ Alle Dateien wurden erfolgreich erstellt!"
