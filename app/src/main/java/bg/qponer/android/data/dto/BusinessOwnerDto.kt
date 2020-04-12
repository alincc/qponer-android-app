package bg.qponer.android.data.dto

import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class BusinessResponse(
    val id: Long,
    val phone: String,
    val logoUrl: String? = null,
    val pictureUrl: String? = null,
    val type: BusinessTypeDto,
    val name: String,
    val description: String,
    val additionalBenefits: String,
    val websiteUrl: String
)

enum class BusinessTypeDto {
    RESTAURANT, BAR, DISCO, CAFE
}

@JsonClass(generateAdapter = true)
data class RankedContributorResponse(
    val name: String,
    val amount: BigDecimal
)