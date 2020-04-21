package bg.qponer.android.data.repository

import bg.qponer.android.auth.SessionStore
import bg.qponer.android.data.dto.CardRegistrationRequest
import bg.qponer.android.data.model.Card
import bg.qponer.android.data.model.CardRegistrationData
import bg.qponer.android.data.service.CardService
import bg.qponer.android.util.Result
import com.mangopay.android.sdk.model.CardRegistration

class CardRepository(
    private val cardService: CardService,
    private val sessionStore: SessionStore
) {

    suspend fun startRegistration(): Result<CardRegistrationData> = runServiceMethod {
        val userId =
            sessionStore.user?.userId ?: throw IllegalStateException("Invalid user session")

        cardService.createCardRegistration(userId)
            .let {
                CardRegistrationData(
                    accessKey = it.accessKey,
                    baseUrl = it.baseUrl,
                    cardPreregistrationId = it.cardPreregistrationId,
                    cardRegistrationUrl = it.cardRegistrationUrl,
                    cardType = it.cardType,
                    clientId = it.clientId,
                    preregistrationData = it.preregistrationData
                )
            }
    }

    suspend fun finishRegistration(
        cardRegistrationId: Long,
        cardRegistrationData: String,
        number: String,
        expiryDate: String
    ): Result<Card> = runServiceMethod {
        val userId = sessionStore.user?.userId ?: throw IllegalStateException("Invalid user session")
        val cardRegistrationRequest = CardRegistrationRequest(
            cardRegistrationData,
            number,
            expiryDate
        )
        cardService.finishCardRegistration(userId, cardRegistrationId, cardRegistrationRequest)
            .let {
                Card(
                    id = it.id,
                    displayName = it.displayName,
                    expiryDate = it.expiryDate
                )
            }
    }

}