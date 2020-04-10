package bg.qponer.android.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class VoucherResponse(
    val id: Long,
    val businessName: String,
    val contributorName: String,
    val value: BigDecimal
)