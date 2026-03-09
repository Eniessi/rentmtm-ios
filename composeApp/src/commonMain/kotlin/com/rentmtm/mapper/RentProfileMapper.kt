package com.rentmtm.mapper

import com.rentmtm.model.RentProfile
import com.rentmtm.db.RentProfileEntity

fun RentProfileEntity.toDomainModel(): RentProfile {
    return RentProfile(
        id = this.idRentProfile,
        description = this.description,
        profileDetails = this.profileDetails
    )
}

fun RentProfile.toEntity(): RentProfileEntity {
    return RentProfileEntity(
        idRentProfile = this.id,
        description = this.description,
        profileDetails = this.profileDetails
    )
}