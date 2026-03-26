package com.rentmtm.mapper

import com.rentmtm.model.EmergencyContact
import com.rentmtm.db.EmergencyContact as DbEmergencyContact

fun DbEmergencyContact.toDomainModel(): EmergencyContact {
    return EmergencyContact(
        id = this.id.toInt(),
        name = this.name,
        relationship = this.relationship,
        phoneNumber = this.phoneNumber
    )
}

fun EmergencyContact.toEntity(): DbEmergencyContact {
    return DbEmergencyContact(
        id = this.id.toLong(),
        name = this.name,
        relationship = this.relationship,
        phoneNumber = this.phoneNumber
    )
}