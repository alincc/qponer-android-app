package bg.qponer.android.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardResponse(
    val id: Long,
    val displayName: String,
    val expiryDate: String
)

@JsonClass(generateAdapter = true)
data class CardRegistrationResponse(
    val accessKey: String,
    val baseUrl: String,
    val cardPreregistrationId: String,
    val cardRegistrationUrl: String,
    val cardType: String,
    val clientId: String,
    val preregistrationData: String
)

@JsonClass(generateAdapter = true)
data class CardRegistrationRequest(
    val registrationData: String,
    val number: String,
    val expiryDate: String
)