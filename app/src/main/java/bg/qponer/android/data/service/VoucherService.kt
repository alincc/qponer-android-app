package bg.qponer.android.data.service

import bg.qponer.android.data.dto.VoucherTypeResponse
import retrofit2.http.GET

interface VoucherService {

    @GET("api/v1/vouchers/types")
    suspend fun getVoucherTypes(): List<VoucherTypeResponse>

}