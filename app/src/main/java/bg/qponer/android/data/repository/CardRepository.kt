package bg.qponer.android.data.repository

import bg.qponer.android.auth.SessionStore
import bg.qponer.android.data.dto.CardRegistrationRequest
import bg.qponer.android.data.model.Card
import bg.qponer.android.data.model.CardRegistrationData
import bg.qponer.android.data.service.CardService
import bg.qponer.android.util.Result

class CardRepository(
    private val cardService: CardService,
    private val sessionStore: SessionStore
) {

    suspend fun startRegistration(): Result<CardRegistrationData> = runServiceMethod {
        val userId =
            sessionStore.user?.userId ?: throw IllegalStateException("Invalid user session")

        cardService.getCardRegistrationDetails(userId)
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
        cardId: String,
        number: String,
        expiryDate: String
    ): Result<Card> = runServiceMethod {
        val userId = sessionStore.user?.userId ?: throw IllegalStateException("Invalid user session")
        val cardRegistrationRequest = CardRegistrationRequest(
            cardId,
            number,
            expiryDate
        )
        cardService.createCard(userId, cardRegistrationRequest)
            .let {
                Card(
                    id = it.id,
                    displayName = it.displayName,
                    expiryDate = it.expiryDate
                )
            }
    }

}