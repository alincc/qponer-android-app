package bg.qponer.android.data.repository

import bg.qponer.android.data.model.Voucher
import bg.qponer.android.data.service.ContributorService
import bg.qponer.android.util.Result

class ContributorRepository(
    private val contributorService: ContributorService
) {

    suspend fun findAllVouchers(userId: Long): Result<List<Voucher>> = runServiceMethod {
        contributorService.findVouchers(userId)
            .map {
                Voucher(
                    it.id,
                    it.businessName,
                    it.value
                )
            }
    }

}