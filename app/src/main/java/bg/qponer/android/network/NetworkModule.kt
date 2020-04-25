package bg.qponer.android.network

import android.content.Context
import android.net.Uri
import bg.qponer.android.BuildConfig
import bg.qponer.android.R
import bg.qponer.android.auth.AuthModule
import bg.qponer.android.auth.SessionStore
import bg.qponer.android.network.json.BigDecimalJsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.X509TrustManager

class NetworkModule(
    context: Context,
    authModule: AuthModule
) {

    private val moshi = Moshi.Builder()
        .add(BigDecimalJsonAdapter())
        .build()

    val retrofit = createRetrofit(context, moshi, authModule.session)

    private fun createRetrofit(context: Context, moshi: Moshi, session: SessionStore): Retrofit {
        val sslSocketFactory = TLSSocketFactory.create(context, listOf(R.raw.certificate))

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(RequestAuthorizationInterceptor(session))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            )
            .sslSocketFactory(
                sslSocketFactory,
                sslSocketFactory.trustManagers[0] as X509TrustManager
            )
            .hostnameVerifier(HostnameVerifier { hostname, session ->
                val verifier = HttpsURLConnection.getDefaultHostnameVerifier()
                if (Uri.parse(BASE_URL).host == hostname) {
                    verifier.verify(PINNED_HOSTNAME, session)
                } else {
                    verifier.verify(hostname, session)
                }
            })
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
//            .baseUrl("http://192.168.0.105:8080")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    companion object {
        private const val BASE_URL =
            "https://qpon-dev-lb-1297959894.eu-central-1.elb.amazonaws.com/"

        private const val PINNED_HOSTNAME = "dev.qpon.bg"
    }

}