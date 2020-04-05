package bg.qponer.android.data.repository

import bg.qponer.android.data.dto.BusinessTypeDto
import bg.qponer.android.data.model.BusinessOwner
import bg.qponer.android.data.model.BusinessType
import bg.qponer.android.data.model.RankedContributor
import bg.qponer.android.data.service.BusinessOwnerService
import bg.qponer.android.util.Result

class BusinessOwnerRepository(
    private val businessOwnerService: BusinessOwnerService
) {

    suspend fun findBusinessOwners(): Result<List<BusinessOwner>> = runServiceMethod {
        businessOwnerService.findBusinessOwners()
            .map { ownerResponse ->
                val topContributors =
                    businessOwnerService.findTopContributorsForBusiness(ownerResponse.id)
                BusinessOwner(
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