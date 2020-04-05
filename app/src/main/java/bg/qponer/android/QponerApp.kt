package bg.qponer.android

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bg.qponer.android.data.SessionStore
import bg.qponer.android.data.dto.json.BigDecimalJsonAdapter
import bg.qponer.android.data.model.AuthenticatedUserModel
import bg.qponer.android.data.repository.AuthRepository
import bg.qponer.android.data.repository.BusinessOwnerRepository
import bg.qponer.android.data.service.AuthService
import bg.qponer.android.data.service.BusinessOwnerService
import bg.qponer.android.ui.home.HomeViewModel
import bg.qponer.android.ui.login.LoginViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class QponerApp : Application() {

    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        val module = AppModule(this)
        viewModelFactory = ViewModelFactory(module)
    }

}

class ViewModelFactory(
    private val appModule: AppModule
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(appModule.provideAuthRepository())
            HomeViewModel::class.java -> HomeViewModel(appModule.provideBusinessOwnerRepository())
            else -> throw IllegalArgumentException("Unknown view model class: ${modelClass.canonicalName}")
        } as T

}

class AppModule(private val qponerApp: QponerApp) {

    private val moshi = provideMoshi()

    private val retrofit = provideRetrofit(moshi)

    private val session = provideTokenStore()

    fun provideAuthRepository() = AuthRepository(
        provideAuthService(),
        session
    )

    fun provideBusinessOwnerRepository() = BusinessOwnerRepository(
        provideBusinessOwnerService()
    )

    private fun provideAuthService() = retrofit.create(AuthService::class.java)

    private fun provideBusinessOwnerService() = retrofit.create(BusinessOwnerService::class.java)

    private fun provideMoshi() =
        Moshi.Builder()
            .add(BigDecimalJsonAdapter())
            .build()

    private fun provideRetrofit(moshi: Moshi): Retrofit {
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

    private fun provideTokenStore(): SessionStore = object : SessionStore {

        override var jwtToken: String? = null
            get() {
                return field ?: readJwtFromPrefs()?.also { field = it }
            }
            set(value) {
                field = value
                field?.let { writeJwtToPrefs(it) }
            }

        override var user: AuthenticatedUserModel? = null
            get() {
                return field ?: readUserFromPrefs()?.also { field = it }
            }
            set(value) {
                field = value
                field?.let { writeUserToPrefs(it) }
            }

        private fun writeJwtToPrefs(jwtToken: String) {
            openPrefs()
                .edit()
                .putString("jwtToken", jwtToken)
                .apply()
        }

        private fun readJwtFromPrefs() =
            openPrefs()
                .getString("jwtToken", null)

        private fun writeUserToPrefs(user: AuthenticatedUserModel) =
            openPrefs()
                .edit()
                .putLong("userId", user.userId)
                .putString("username", user.username)
                .apply()

        private fun readUserFromPrefs(): AuthenticatedUserModel? {
            val userId = openPrefs().getLong("userId", -1)
            val username = openPrefs().getString("username", null)

            return username?.let { AuthenticatedUserModel(userId, username) }
        }

        private fun openPrefs() =
            qponerApp.getSharedPreferences("sessionStore", Context.MODE_PRIVATE)


    }

}