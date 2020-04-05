package bg.qponer.android.data.model

import java.math.BigDecimal

data class BusinessOwner(
    val id: Long,
    val businessName: String,
    val businessDescription: String,
    val type: BusinessType,
    val topContributors: List<RankedContributor>,
    val avatarUrl: String = "https://picsum.photos/344/194"
)

enum class BusinessType {
    RESTAURANT, BAR, DISCO
}

data class RankedContributor(
    val username: String,
    val amount: BigDecimal
)