package bg.qponer.android.data.dto

import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class BusinessOwnerResponse(
    val id: Long,
    val businessName: String,
    val businessDescription: String,
    val avatarUrl: String?,
    val type: BusinessTypeDto
)

enum class BusinessTypeDto {
    RESTAURANT, BAR, DISCO
}

@JsonClass(generateAdapter = true)
data class RankedContributorResponse(
    val name: String,
    val amount: BigDecimal
)