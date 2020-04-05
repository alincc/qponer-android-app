package bg.qponer.android.data.service

import bg.qponer.android.data.dto.BusinessOwnerResponse
import bg.qponer.android.data.dto.RankedContributorResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BusinessOwnerService {

    @GET("api/v1/businessOwners")
    suspend fun findBusinessOwners(): List<BusinessOwnerResponse>

    @GET("api/v1/businessOwners/{id}/top-contributors")
    suspend fun findTopContributorsForBusiness(@Path("id") ownerId: Long): List<RankedContributorResponse>
}