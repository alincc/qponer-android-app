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
                    phone = ownerResponse.phone,
                    logoUrl = ownerResponse.logoUrl,
                    pictureUrl = ownerResponse.pictureUrl,
                    type = ownerResponse.type.map(),
                    name = ownerResponse.name,
                    description = ownerResponse.description,
                    additionalBenefits = ownerResponse.additionalBenefits,
                    websiteUrl = ownerResponse.websiteUrl,
                    topContributors = topContributors.map { RankedContributor(it.name, it.amount) }
                )
            }
    }

    private fun BusinessTypeDto.map() =
        when (this) {
            BusinessTypeDto.BAR -> BusinessType.BAR
            BusinessTypeDto.DISCO -> BusinessType.DISCO
            BusinessTypeDto.RESTAURANT -> BusinessType.RESTAURANT
            BusinessTypeDto.CAFE -> BusinessType.CAFE
        }
}