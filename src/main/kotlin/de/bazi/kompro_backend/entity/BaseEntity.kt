package de.bazi.kompro_backend.entity
import jakarta.persistence.*
import jakarta.persistence.Id
import jakarta.persistence.Version
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
