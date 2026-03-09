package com.rentmtm.mapper

import com.rentmtm.model.*
import com.rentmtm.model.enums.*
import com.rentmtm.db.ProfessionalEntity

fun ProfessionalEntity.toDomainModel(
    certifications: List<Certification> = emptyList(),
    skills: List<String> = emptyList(),
    languages: List<Language> = emptyList(),
    references: List<Reference> = emptyList(),
    workHistory: List<Employment> = emptyList()
): Professional {
    return Professional(
        id = this.idProfessional.toInt(),
        fullName = this.fullName,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender?.let { Gender.valueOf(it) },
        nationality = this.nationality,
        primaryLanguages = this.primaryLanguages,
        email = this.email,
        phoneNumber = this.phoneNumber,

        address = Address(
            street = this.street,
            city = this.city,
            state = this.state,
            zipCode = this.zipCode,
            country = this.country
        ),

        nationalIdNumber = this.nationalIdNumber,
        passportNumber = this.passportNumber,
        issuingCountry = this.issuingCountry,
        expirationDate = this.expirationDate,
        socialSecurityNumber = this.socialSecurityNumber,

        emergencyContact = EmergencyContact(
            name = this.emergencyName,
            relationship = this.emergencyRelationship,
            phoneNumber = this.emergencyPhone
        ),

        professionalTitle = this.professionalTitle,
        fieldOfWork = this.fieldOfWork,
        yearsOfExperience = this.yearsOfExperience?.toInt(),
        employmentType = this.employmentType?.let { EmploymentType.valueOf(it) },
        otherEmploymentType = this.otherEmploymentType,
        companyName = this.companyName,

        businessAddress = Address(
            street = this.businessStreet,
            city = this.businessCity,
            state = this.businessState,
            zipCode = this.businessZipCode,
            country = this.businessCountry
        ),

        workAuthorization = this.workAuthorization?.let { WorkAuthorization.valueOf(it) },
        workVisaType = this.workVisaType,
        otherWorkAuthorization = this.otherWorkAuthorization,
        expectedSalary = this.expectedSalary,
        salaryPeriod = this.salaryPeriod?.let { SalaryPeriod.valueOf(it) },
        educationLevel = this.educationLevel?.let { EducationLevel.valueOf(it) },
        otherEducation = this.otherEducation,
        fieldOfStudy = this.fieldOfStudy,

        certifications = certifications,
        skills = skills,
        languages = languages,
        references = references,
        workHistory = workHistory,

        availabilityToStart = this.availabilityToStart,
        workSchedule = this.workSchedule?.let { WorkSchedule.valueOf(it) },
        willingToRelocate = this.willingToRelocate,
        hasDriverLicense = this.hasDriverLicense,
        willingToTravel = this.willingToTravel,
        hasLegalWorkRestrictions = this.hasLegalWorkRestrictions,
        additionalComments = this.additionalComments,
        signature = this.signature,
        signatureDate = this.signatureDate,
        professionalType = this.professionalType?.let { ProfessionalType.valueOf(it) },
        otherProfessionalType = this.otherProfessionalType
    )
}