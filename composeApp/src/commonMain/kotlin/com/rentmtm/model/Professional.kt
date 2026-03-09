package com.rentmtm.model

import com.rentmtm.model.enums.EducationLevel
import com.rentmtm.model.enums.EmploymentType
import com.rentmtm.model.enums.Gender
import com.rentmtm.model.enums.ProfessionalType
import com.rentmtm.model.enums.SalaryPeriod
import com.rentmtm.model.enums.WorkAuthorization
import com.rentmtm.model.enums.WorkSchedule
import kotlinx.serialization.Serializable

@Serializable
data class Professional(
    var id: Int = 0,

    var fullName: String? = null,
    var dateOfBirth: String? = null,
    var gender: Gender? = null,
    var nationality: String? = null,
    var primaryLanguages: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var address: Address? = null,
    var nationalIdNumber: String? = null,
    var passportNumber: String? = null,
    var issuingCountry: String? = null,
    var expirationDate: String? = null,
    var socialSecurityNumber: String? = null,
    var emergencyContact: EmergencyContact? = null,

    var professionalTitle: String? = null,
    var fieldOfWork: String? = null,
    var yearsOfExperience: Int? = null,
    var employmentType: EmploymentType? = null,
    var otherEmploymentType: String? = null,
    var companyName: String? = null,
    var businessAddress: Address? = null,
    var workAuthorization: WorkAuthorization? = null,
    var workVisaType: String? = null,
    var otherWorkAuthorization: String? = null,
    var expectedSalary: Double? = null,
    var salaryPeriod: SalaryPeriod? = null,

    var educationLevel: EducationLevel? = null,
    var otherEducation: String? = null,
    var fieldOfStudy: String? = null,
    var certifications: List<Certification> = emptyList(),
    var skills: List<String> = emptyList(),
    var languages: List<Language> = emptyList(),
    var references: List<Reference> = emptyList(),

    var workHistory: List<Employment> = emptyList(),

    var availabilityToStart: String? = null,
    var workSchedule: WorkSchedule? = null,
    var willingToRelocate: Boolean = false,
    var hasDriverLicense: Boolean = false,
    var willingToTravel: Boolean = false,
    var hasLegalWorkRestrictions: Boolean = false,
    var additionalComments: String? = null,
    var signature: String? = null,
    var signatureDate: String? = null,
    var professionalType: ProfessionalType? = null,
    var otherProfessionalType: String? = null
)