import java.util.*

data class ItemResultRequest(
    val itemId: UUID,
    val ratingChoiceId: UUID,
    val note: String? = null
)