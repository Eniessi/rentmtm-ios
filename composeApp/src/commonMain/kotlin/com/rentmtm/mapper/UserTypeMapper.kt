package com.rentmtm.mapper

import com.rentmtm.model.UserType
import com.rentmtm.db.UserType as DbUserType

fun DbUserType.toDomainModel(): UserType {
    return UserType(
        id = this.id.toInt(),
        description = this.description,
        profileDetails = this.profileDetails
    )
}

fun UserType.toEntity(): DbUserType {
    return DbUserType(
        id = this.id.toLong(),
        description = this.description,
        profileDetails = this.profileDetails
    )
}