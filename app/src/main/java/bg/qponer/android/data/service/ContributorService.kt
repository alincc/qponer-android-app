package bg.qponer.android.data.service

import bg.qponer.android.data.dto.VoucherResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ContributorService {

    @GET("api/v1/contributors/{id}/vouchers")
    suspend fun findVouchers(@Path("id") id: Long): List<VoucherResponse>

}