package bg.qponer.android.data.model

import java.math.BigDecimal

data class Voucher(
    val id: Long,
    val businessName: String,
    val value: BigDecimal
)