package com.rentmtm.mapper

import com.rentmtm.model.Employment
import com.rentmtm.db.EmploymentEntity

fun EmploymentEntity.toDomainModel(): Employment {
    return Employment(
        id = this.idEmployment.toInt(),
        employer = this.employer,
        position = this.position,
        startDate = this.startDate,
        endDate = this.endDate,
        reasonForLeaving = this.reasonForLeaving,
        responsibilities = this.responsibilities
    )
}

fun Employment.toEntity(): EmploymentEntity {
    return EmploymentEntity(
        idEmployment = this.id.toLong(),
        employer = this.employer,
        position = this.position,
        startDate = this.startDate,
        endDate = this.endDate,
        reasonForLeaving = this.reasonForLeaving,
        responsibilities = this.responsibilities
    )
}