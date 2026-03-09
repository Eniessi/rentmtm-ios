package com.rentmtm.mapper

import com.rentmtm.model.Reference
import com.rentmtm.db.ReferenceEntity

fun ReferenceEntity.toDomainModel(): Reference {
    return Reference(
        id = this.idReference.toInt(),
        name = this.name,
        company = this.company,
        relationship = this.relationship,
        contactInfo = this.contactInfo
    )
}

fun Reference.toEntity(): ReferenceEntity {
    return ReferenceEntity(
        idReference = this.id.toLong(),
        name = this.name,
        company = this.company,
        relationship = this.relationship,
        contactInfo = this.contactInfo
    )
}