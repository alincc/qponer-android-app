package bg.qponer.android.data.service

import bg.qponer.android.network.NetworkModule

class ServiceModule(
    private val networkModule: NetworkModule
) {

    fun createAuthService(): AuthService = networkModule.retrofit.create(AuthService::class.java)

    fun createBusinessOwnerService(): BusinessService =
        networkModule.retrofit.create(BusinessService::class.java)

    fun createContributorService(): ContributorService =
        networkModule.retrofit.create(ContributorService::class.java)

    fun createVoucherService(): VoucherService =
        networkModule.retrofit.create(VoucherService::class.java)
}