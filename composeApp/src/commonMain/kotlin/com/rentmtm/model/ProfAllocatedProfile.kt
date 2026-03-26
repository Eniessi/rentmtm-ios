package com.rentmtm.model

import com.rentmtm.model.enums.*
import kotlinx.serialization.Serializable

@Serializable
data class ProfAllocatedProfile(
    var id: Int = 0,

    // ==========================================
    // DADOS MOVIDOS PARA A CLASSE USER (Comentados para histórico)
    // ==========================================
    // var fullName: String? = null,
    // var dateOfBirth: String? = null,
    // var gender: Gender? = null,
    // var nationality: String? = null,
    // var primaryLanguages: String? = null,
    // var email: String? = null,
    // var phoneNumber: String? = null,
    // var address: Address? = null,
    // var nationalIdNumber: String? = null,
    // var passportNumber: String? = null,
    // var issuingCountry: String? = null,
    // var expirationDate: String? = null,
    // var socialSecurityNumber: String? = null,
    // var emergencyContact: EmergencyContact? = null,

    // ==========================================
    // DADOS ESTRITAMENTE PROFISSIONAIS (Ativos)
    // ==========================================
    var professionalTitle: String? = null,
    var fieldOfWork: String? = null,
    var yearsOfExperience: Int? = null,
    var employmentType: EmploymentType? = null,
    var otherEmploymentType: String? = null,
    var companyName: String? = null,
    var businessAddress: Address? = null, // Endereço de onde trabalha

    var workAuthorization: WorkAuthorization? = null,
    var workVisaType: String? = null,
    var otherWorkAuthorization: String? = null,

    var expectedSalary: Double? = null,
    var salaryPeriod: SalaryPeriod? = null,

    // Qualificações e Listas
    var educationLevel: EducationLevel? = null,
    var otherEducation: String? = null,
    var fieldOfStudy: String? = null,
    var certifications: List<Certification> = emptyList(),
    var skills: List<String> = emptyList(),
    var languages: List<Language> = emptyList(),
    var references: List<Reference> = emptyList(),
    var workHistory: List<Employment> = emptyList(),

    // Disponibilidade e Condições
    var availabilityToStart: String? = null,
    var workSchedule: WorkSchedule? = null,
    var willingToRelocate: Boolean = false,
    var hasDriverLicense: Boolean = false,
    var willingToTravel: Boolean = false,
    var hasLegalWorkRestrictions: Boolean = false,
    var additionalComments: String? = null
)