package bg.qponer.android.network

import bg.qponer.android.BuildConfig
import bg.qponer.android.auth.AuthModule
import bg.qponer.android.auth.SessionStore
import bg.qponer.android.network.json.BigDecimalJsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkModule(
    authModule: AuthModule
) {

    private val moshi = Moshi.Builder()
        .add(BigDecimalJsonAdapter())
        .build()

    val retrofit = createRetrofit(moshi, authModule.session)

    private fun createRetrofit(moshi: Moshi, session: SessionStore): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            )
            .addInterceptor {
                val request = session.jwtToken?.let { jwtToken ->
                    it.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer $jwtToken")
                        .build()
                } ?: it.request()

                it.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl("http://192.168.0.105:8080")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}