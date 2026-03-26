package com.rentmtm.mapper

import com.rentmtm.model.*
import com.rentmtm.db.ProfAllocatedProfile as DbProfProfile

object ProfAllocatedMapper {
    fun toDomain(
        dbProfile: DbProfProfile,
        businessAddress: Address? = null,
        certs: List<Certification> = emptyList(),
        skills: List<String> = emptyList(),
        langs: List<Language> = emptyList(),
        refs: List<Reference> = emptyList(),
        history: List<Employment> = emptyList()
    ): ProfAllocatedProfile {
        return ProfAllocatedProfile(
            id = dbProfile.id.toInt(),
            professionalTitle = dbProfile.professionalTitle,
            fieldOfWork = dbProfile.fieldOfWork,
            yearsOfExperience = dbProfile.yearsOfExperience?.toInt(),
            employmentType = dbProfile.employmentType,
            otherEmploymentType = dbProfile.otherEmploymentType,
            companyName = dbProfile.companyName,
            businessAddress = businessAddress,
            workAuthorization = dbProfile.workAuthorization,
            workVisaType = dbProfile.workVisaType,
            otherWorkAuthorization = dbProfile.otherWorkAuthorization,
            expectedSalary = dbProfile.expectedSalary,
            salaryPeriod = dbProfile.salaryPeriod,
            educationLevel = dbProfile.educationLevel,
            otherEducation = dbProfile.otherEducation,
            fieldOfStudy = dbProfile.fieldOfStudy,
            certifications = certs,
            skills = skills,
            languages = langs,
            references = refs,
            workHistory = history,
            availabilityToStart = dbProfile.availabilityToStart,
            workSchedule = dbProfile.workSchedule,
            willingToRelocate = dbProfile.willingToRelocate,
            hasDriverLicense = dbProfile.hasDriverLicense,
            willingToTravel = dbProfile.willingToTravel,
            hasLegalWorkRestrictions = dbProfile.hasLegalWorkRestrictions,
            additionalComments = dbProfile.additionalComments
        )
    }
}