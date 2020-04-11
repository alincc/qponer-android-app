package bg.qponer.android.data.repository

import bg.qponer.android.data.model.VoucherType
import bg.qponer.android.data.service.VoucherService
import bg.qponer.android.util.Result

class VoucherRepository(
    private val voucherService: VoucherService
) {

    suspend fun getVoucherTypes(): Result<List<VoucherType>> = runServiceMethod {
        voucherService.getVoucherTypes()
            .map {
                VoucherType(it.id, it.name)
            }
    }

}