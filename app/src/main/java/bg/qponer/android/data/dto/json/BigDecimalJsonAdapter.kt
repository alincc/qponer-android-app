package bg.qponer.android.data.dto.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

class BigDecimalJsonAdapter {

    @FromJson
    fun fromJson(value: String) = BigDecimal(value)

    @ToJson
    fun toJson(value: BigDecimal) = value.toString()
}