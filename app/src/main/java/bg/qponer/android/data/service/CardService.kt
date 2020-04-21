package bg.qponer.android.data.service

import bg.qponer.android.data.dto.CardRegistrationRequest
import bg.qponer.android.data.dto.CardRegistrationResponse
import bg.qponer.android.data.dto.CardResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CardService {

    @POST("api/v1/contributors/{id}/cards")
    suspend fun createCardRegistration(@Path("id") userId: Long): CardRegistrationResponse

    @POST("api/v1/contributors/{id}/cards/{cardRegistrationId}")
    suspend fun finishCardRegistration(
        @Path("id") userId: Long,
        @Path("cardRegistrationId") cardRegistrationId: Long,
        @Body body: CardRegistrationRequest
    ): CardResponse

}