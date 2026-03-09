package com.rentmtm.mapper

import com.rentmtm.model.EmergencyContact
import com.rentmtm.db.EmergencyContactEntity

fun EmergencyContactEntity.toDomainModel(): EmergencyContact {
    return EmergencyContact(
        id = this.idEmergencyContact.toInt(),
        name = this.name,
        relationship = this.relationship,
        phoneNumber = this.phoneNumber
    )
}

fun EmergencyContact.toEntity(): EmergencyContactEntity {
    return EmergencyContactEntity(
        idEmergencyContact = this.id.toLong(),
        name = this.name,
        relationship = this.relationship,
        phoneNumber = this.phoneNumber
    )
}