package bg.qponer.android.network

import bg.qponer.android.auth.SessionStore
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RequestAuthorizationInterceptor(
    private val session: SessionStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = authenticateRequest(chain)
        return chain.proceed(request)
    }

    private fun authenticateRequest(chain: Interceptor.Chain): Request {
        val request = chain.request()
        return when (request.tag(RequestAuthorizationType::class.java)) {
            RequestAuthorizationType.BASIC -> request.addClientCredentialsHeader(
                CLIENT_ID,
                CLIENT_SECRET
            )
            else -> request.addAccessTokenHeader(session.accessToken)
        }
    }


    companion object {
        private const val CLIENT_ID = "client"
        private const val CLIENT_SECRET = "password"
    }

}