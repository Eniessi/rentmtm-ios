package com.rentmtm.mapper

import com.rentmtm.model.MarketplaceProfile
import com.rentmtm.db.MarketplaceProfileEntity

fun MarketplaceProfileEntity.toDomainModel(): MarketplaceProfile {
    return MarketplaceProfile(
        id = this.idMarketplaceProfile,
        description = this.description,
        profileDetails = this.profileDetails
    )
}

fun MarketplaceProfile.toEntity(): MarketplaceProfileEntity {
    return MarketplaceProfileEntity(
        idMarketplaceProfile = this.id,
        description = this.description,
        profileDetails = this.profileDetails
    )
}