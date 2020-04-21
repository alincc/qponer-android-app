package bg.qponer.android.data.model

import java.math.BigDecimal

data class Business(
    val id: Long,
    val phone: String,
    val logoUrl: String? = null,
    val pictureUrl: String? = "https://picsum.photos/344/194",
    val type: BusinessType,
    val name: String,
    val description: String,
    val additionalBenefits: String,
    val websiteUrl: String,
    val topContributors: List<RankedContributor>
)

enum class BusinessType {
    RESTAURANT, BAR, DISCO, CAFE
}

data class RankedContributor(
    val username: String,
    val amount: BigDecimal
)