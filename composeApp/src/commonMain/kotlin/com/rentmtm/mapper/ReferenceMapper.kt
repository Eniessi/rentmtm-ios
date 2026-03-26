package com.rentmtm.mapper

import com.rentmtm.model.Reference
import com.rentmtm.db.Reference as DbReference

fun DbReference.toDomainModel(): Reference {
    return Reference(
        id = this.id.toInt(),
        name = this.name,
        company = this.company,
        relationship = this.relationship,
        contactInfo = this.contactInfo
    )
}

fun Reference.toEntity(): DbReference {
    return DbReference(
        id = this.id.toLong(),
        name = this.name,
        company = this.company,
        relationship = this.relationship,
        contactInfo = this.contactInfo
    )
}