package com.rentmtm.mapper

import com.rentmtm.model.UserType
import com.rentmtm.db.UserTypeEntity

fun UserTypeEntity.toDomainModel(): UserType {
    return UserType(
        id = this.idUserType.toInt(),
        description = this.description,
        profileDetails = this.profileDetails
    )
}

fun UserType.toEntity(): UserTypeEntity {
    return UserTypeEntity(
        idUserType = this.id.toLong(),
        description = this.description,
        profileDetails = this.profileDetails
    )
}