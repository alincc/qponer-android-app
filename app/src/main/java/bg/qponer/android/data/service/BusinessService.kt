package bg.qponer.android.data.service

import bg.qponer.android.data.dto.BusinessResponse
import bg.qponer.android.data.dto.RankedContributorResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BusinessService {

    @GET("api/v1/businesses")
    suspend fun findBusinessOwners(): List<BusinessResponse>

    @GET("api/v1/businesses/{id}/top-contributors")
    suspend fun findTopContributorsForBusiness(@Path("id") ownerId: Long): List<RankedContributorResponse>
}