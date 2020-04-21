package bg.qponer.android.data.model

data class CardRegistrationData(
    val accessKey: String,
    val baseUrl: String,
    val cardPreregistrationId: String,
    val cardRegistrationUrl: String,
    val cardType: String,
    val clientId: String,
    val preregistrationData: String
)