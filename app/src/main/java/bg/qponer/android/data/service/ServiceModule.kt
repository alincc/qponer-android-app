package bg.qponer.android.data.service

import bg.qponer.android.network.NetworkModule
import retrofit2.Retrofit

class ServiceModule(
    private val networkModule: NetworkModule
) {

    fun createAuthService() = networkModule.retrofit.create(AuthService::class.java)

    fun createBusinessOwnerService() = networkModule.retrofit.create(BusinessOwnerService::class.java)

}