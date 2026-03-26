package com.rentmtm.mapper

import com.rentmtm.model.Certification
import com.rentmtm.db.Certification as DbCertification

// 3. De Banco para Domínio
fun DbCertification.toDomain(): Certification {
    return Certification(
        id = this.id.toInt(),
        name = this.name,
        issuingOrganization = this.issuingOrganization,
        expirationDate = this.expirationDate
    )
}

fun Certification.toDbModel(): DbCertification {
    return DbCertification(
        id = this.id.toLong(),
        name = this.name,
        issuingOrganization = this.issuingOrganization,
        expirationDate = this.expirationDate
    )
}