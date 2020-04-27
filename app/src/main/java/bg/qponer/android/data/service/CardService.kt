package bg.qponer.android.data.service

import bg.qponer.android.data.dto.CardRegistrationRequest
import bg.qponer.android.data.dto.CardRegistrationResponse
import bg.qponer.android.data.dto.CardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardService {

    @GET("api/v1/contributors/{id}/cards/registrationDetails")
    suspend fun getCardRegistrationDetails(@Path("id") userId: Long): CardRegistrationResponse

    @POST("api/v1/contributors/{id}/cards")
    suspend fun createCard(
        @Path("id") userId: Long,
        @Body body: CardRegistrationRequest
    ): CardResponse

}