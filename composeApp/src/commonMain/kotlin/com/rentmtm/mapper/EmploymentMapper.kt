package com.rentmtm.mapper

import com.rentmtm.model.Employment
import com.rentmtm.db.Employment as DbEmployment

fun DbEmployment.toDomainModel(): Employment {
    return Employment(
        id = this.id.toInt(),
        employer = this.employer,
        position = this.position,
        startDate = this.startDate,
        endDate = this.endDate,
        reasonForLeaving = this.reasonForLeaving,
        responsibilities = this.responsibilities
    )
}

fun Employment.toEntity(): DbEmployment {
    return DbEmployment(
        id = this.id.toLong(),
        employer = this.employer,
        position = this.position,
        startDate = this.startDate,
        endDate = this.endDate,
        reasonForLeaving = this.reasonForLeaving,
        responsibilities = this.responsibilities
    )
}