package bg.qponer.android.network

import okhttp3.Credentials
import okhttp3.Request

private const val HEADER_AUTHORIZATION = "Authorization"

private fun bearer(token: String) = "Bearer $token"

internal fun Request.addClientCredentialsHeader(clientId: String, clientSecret: String) =
    addAuthorizationHeader(Credentials.basic(clientId, clientSecret))

internal fun Request.addAccessTokenHeader(token: String?) =
    token?.let {
        addAuthorizationHeader(bearer(token))
    } ?: this

private fun Request.addAuthorizationHeader(credentials: String) =
    newBuilder()
        .addHeader(HEADER_AUTHORIZATION, credentials)
        .build()

