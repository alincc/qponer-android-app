package bg.qponer.android.network

import bg.qponer.android.auth.SessionStore
import okhttp3.Interceptor
import okhttp3.Response

class RequestAuthorizationInterceptor(
    private val session: SessionStore
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = session.jwtToken?.let { jwtToken ->
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $jwtToken")
                .build()
        } ?: chain.request()

        return chain.proceed(request)
    }

}