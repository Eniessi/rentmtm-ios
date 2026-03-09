package com.rentmtm.mapper

import com.rentmtm.model.Certification
import com.rentmtm.db.CertificationEntity


fun CertificationEntity.toDomainModel(): Certification {
    return Certification(
        id = this.idCertification.toInt(),
        name = this.name,
        issuingOrganization = this.issuingOrganization,
        expirationDate = this.expirationDate
    )
}

fun Certification.toEntity(): CertificationEntity {
    return CertificationEntity(
        idCertification = this.id.toLong(),
        name = this.name,
        issuingOrganization = this.issuingOrganization,
        expirationDate = this.expirationDate
    )
}