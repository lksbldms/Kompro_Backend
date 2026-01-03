package de.bazi.kompro_backend.repository
import de.bazi.kompro_backend.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface ItemRepository : JpaRepository<Item, UUID>
