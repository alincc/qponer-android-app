package bg.qponer.android.data.repository

import bg.qponer.android.data.dto.BusinessTypeDto
import bg.qponer.android.data.model.Business
import bg.qponer.android.data.model.BusinessType
import bg.qponer.android.data.model.RankedContributor
import bg.qponer.android.data.service.BusinessService
import bg.qponer.android.util.Result

class BusinessOwnerRepository(
    private val businessService: BusinessService
) {

    suspend fun findBusinessOwners(): Result<List<Business>> = runServiceMethod {
        businessService.findBusinessOwners()
            .map { ownerResponse ->
                val topContributors =
                    businessService.findTopContributorsForBusiness(ownerResponse.id)
                Business(
                    id = ownerResponse.id,
                    businessName = ownerResponse.businessName,
                    businessDescription = ownerResponse.businessDescription,
                    type = ownerResponse.type.map(),
                    topContributors = topContributors.map { RankedContributor(it.name, it.amount) }
                )
            }
    }

    private fun BusinessTypeDto.map() =
        when (this) {
            BusinessTypeDto.BAR -> BusinessType.BAR
            BusinessTypeDto.DISCO -> BusinessType.DISCO
            BusinessTypeDto.RESTAURANT -> BusinessType.RESTAURANT
        }
}