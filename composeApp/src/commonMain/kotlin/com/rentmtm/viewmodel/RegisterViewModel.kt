package com.rentmtm.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.utils.ImageStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Clock

// ==========================================
// MOLDES DA API (Zippopotam.us)
// ==========================================
@Serializable
data class UsZipCodeResponse(
    @SerialName("post code") val postCode: String? = null,
    val country: String? = null,
    @SerialName("country abbreviation") val countryAbbreviation: String? = null,
    val places: List<Place>? = null
)

@Serializable
data class Place(
    @SerialName("place name") val placeName: String? = null,
    val longitude: String? = null,
    val state: String? = null,
    @SerialName("state abbreviation") val stateAbbreviation: String? = null,
    val latitude: String? = null
)

// ==========================================
// CLASSES DE ESTADO (DATA CLASSES)
// ==========================================

enum class ProfileType(val displayName: String) {
    NONE("User"),
    SELLER("Seller"),
    BUYER("Buyer"),
    PARTNER("Partner"),
    PROFESSIONAL("Professional"),
    OWNER("Owner"),
    TENANT("Tenant")
}

data class IdentityDetailsState(
    val idType: String = "",
    val issueDate: String = "",
    val expirationDate: String = "",
    val holderName: String = "",
    val licenseNumber: String = "",
    val licenseClass: String = "",
    val acceptedTerms: Boolean = false
)

data class PersonalInfoState(
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val nationality: String = "",
    val primaryLanguage: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = ""
)

data class CertificationEntry(
    val certName: String = "",
    val certOrg: String = "",
    val certDate: String = ""
)

data class AddressState(
    val street: String = "",
    val number: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val apartment: String = ""
)

data class GovernmentIdState(
    val hasNationalId: Boolean = false,
    val nationalId: String = "",
    val hasPassport: Boolean = false,
    val passportNumber: String = "",
    val issuingCountry: String = "",
    val passportExpirationDate: String = "",
    val ssn: String = ""
)

data class EmergencyContactState(
    val name: String = "",
    val relationship: String = "",
    val phone: String = ""
)

data class ProfessionalInfoState(
    val selectedOccupation: String? = null,
    val occupationTitle: String = "",
    val fieldOfWork: String = "",
    val yearsExperience: String = "",
    val companyName: String = "",
    val isFullTime: Boolean = false,
    val isPartTime: Boolean = false,
    val isFreelancer: Boolean = false,
    val isContractor: Boolean = false,
    val isOtherEmployment: Boolean = false,
    val otherEmploymentText: String = "",
    val isCitizen: Boolean = false,
    val isPermanentResident: Boolean = false,
    val isVisaHolder: Boolean = false,
    val visaType: String = "",
    val isOtherAuth: Boolean = false,
    val otherAuthText: String = "",
    val salaryAmount: String = "",
    val salaryPeriod: String = ""
)

data class LanguageEntry(
    val language: String = "",
    val fluency: String = ""
)

data class ReferenceEntry(
    val name: String = "",
    val company: String = "",
    val relationship: String = "",
    val contact: String = ""
)

data class QualificationsState(
    val isHighSchool: Boolean = false,
    val isAssociate: Boolean = false,
    val isBachelor: Boolean = false,
    val isMaster: Boolean = false,
    val isDoctorate: Boolean = false,
    val isOtherEdu: Boolean = false,
    val otherEduText: String = "",
    val fieldOfStudy: String = "",
    val isIT: Boolean = false,
    val isEng: Boolean = false,
    val isHealth: Boolean = false,
    val isFin: Boolean = false,
    val isConst: Boolean = false,
    val isMark: Boolean = false,
    val isOtherSkill: Boolean = false,
    val otherSkillText: String = "",
    val certifications: List<CertificationEntry> = listOf(CertificationEntry()),
    val languages: List<LanguageEntry> = listOf(LanguageEntry()),
    val references: List<ReferenceEntry> = listOf(ReferenceEntry())
)

data class EmploymentEntry(
    val employerName: String = "",
    val position: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val responsibilities: String = "",
    val reasonForLeaving: String = ""
)

data class EmploymentHistoryState(
    val experiences: List<EmploymentEntry> = listOf(EmploymentEntry())
)

data class AdditionalInfoState(
    val availabilityDate: String = "",
    val isFlexible: Boolean = false,
    val isFixedHours: Boolean = false,
    val relocate: String = "",
    val validLicense: String = "",
    val travel: String = "",
    val legalRestrictions: String = "",
    val additionalComments: String = "",
    val signature: String = "",
    val signatureDate: String = ""
)

data class IdentityPhotosState(
    val selectedMethod: String? = null,
    val idFrontPath: String? = null,
    val idBackPath: String? = null,
    val selfiePath: String? = null
)

// ==========================================
// O CÉREBRO (VIEWMODEL)
// ==========================================
class RegisterViewModel : ViewModel() { // ⬅️ Agora é um ViewModel de verdade!

    var profileType by mutableStateOf(ProfileType.NONE)

    var identityPhotos by mutableStateOf(IdentityPhotosState())
    var identityDetails by mutableStateOf(IdentityDetailsState())
    var personalInfo by mutableStateOf(PersonalInfoState())
    var address by mutableStateOf(AddressState())
    var businessAddress by mutableStateOf(AddressState())
    var governmentId by mutableStateOf(GovernmentIdState())
    var emergencyContact by mutableStateOf(EmergencyContactState())
    var professionalInfo by mutableStateOf(ProfessionalInfoState())
    var qualifications by mutableStateOf(QualificationsState())
    var employmentHistory by mutableStateOf(EmploymentHistoryState())
    var additionalInfo by mutableStateOf(AdditionalInfoState())

    // Instância do Ktor para fazer as chamadas de API
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }) // Ignora dados extras do JSON que não usamos
        }
    }

    // ==========================================
    // LÓGICA DE NEGÓCIO E APIs
    // ==========================================

    fun saveDocumentPhoto(bytes: ByteArray, documentType: String) {
        viewModelScope.launch {
            val timestamp = Clock.System.now().toEpochMilliseconds()
            val fileName = "doc_${documentType}_${timestamp}.jpg"

            try {
                // Vai para a thread de I/O silenciosamente e volta com o caminho
                val savedPath = ImageStorage.saveImageToCache(bytes, fileName)
                println("Tech Lead Log -> Physical image saved in: $savedPath")

                // Aqui tu atualizas o estado da tua tela.
                // (Adapte as variáveis abaixo de acordo com o teu data class IdentityPhotos)
                identityPhotos = when (documentType) {
                    "front" -> identityPhotos.copy(idFrontPath = savedPath)
                    "back" -> identityPhotos.copy(idBackPath = savedPath)
                    "selfie" -> identityPhotos.copy(selfiePath = savedPath)
                    else -> identityPhotos
                }
            } catch (e: Exception) {
                println("Tech Lead Log -> Erro catastrófico ao salvar imagem: ${e.message}")
            }
        }
    }

    fun updateProfileType(type: ProfileType) {
        profileType = type
    }

    // --- Endereço Residencial ---
    fun onZipCodeChanged(newZip: String) {
        val cleanZip = newZip.filter { it.isDigit() }.take(5) // Limita a 5 dígitos (US)

        address = address.copy(zipCode = cleanZip)

        if (address.zipCode.length == 5) {
            fetchAddressByZip(address.zipCode)
        }
    }

    private fun fetchAddressByZip(zip: String) {
        viewModelScope.launch { // Roda em segundo plano
            try {
                val response: UsZipCodeResponse = client.get("https://api.zippopotam.us/us/$zip").body()

                if (!response.places.isNullOrEmpty()) {
                    val place = response.places.first()
                    address = address.copy(
                        city = place.placeName ?: address.city,
                        state = place.stateAbbreviation ?: address.state
                    )
                }
            } catch (e: Exception) {
                println("Falha na API de Zip Code: ${e.message}")
            }
        }
    }

    // --- Endereço Comercial (Business) ---
    fun onBusinessZipCodeChanged(newZip: String) {
        val cleanZip = newZip.filter { it.isDigit() }.take(5)

        businessAddress = businessAddress.copy(zipCode = cleanZip)

        if (businessAddress.zipCode.length == 5) {
            fetchBusinessAddressByZip(businessAddress.zipCode)
        }
    }

    private fun fetchBusinessAddressByZip(zip: String) {
        viewModelScope.launch {
            try {
                val response: UsZipCodeResponse = client.get("https://api.zippopotam.us/us/$zip").body()

                if (!response.places.isNullOrEmpty()) {
                    val place = response.places.first()
                    businessAddress = businessAddress.copy(
                        city = place.placeName ?: businessAddress.city,
                        state = place.stateAbbreviation ?: businessAddress.state
                    )
                }
            } catch (e: Exception) {
                println("Falha na API de Business Zip Code: ${e.message}")
            }
        }
    }

    // --- Listas Dinâmicas ---
    fun addLanguage() {
        qualifications = qualifications.copy(languages = qualifications.languages + LanguageEntry())
    }

    fun removeLanguage(index: Int) {
        qualifications = qualifications.copy(languages = qualifications.languages.filterIndexed { i, _ -> i != index })
    }

    fun updateLanguage(index: Int, newLanguage: String? = null, newFluency: String? = null) {
        qualifications = qualifications.copy(
            languages = qualifications.languages.mapIndexed { i, oldEntry ->
                if (i == index) oldEntry.copy(language = newLanguage ?: oldEntry.language, fluency = newFluency ?: oldEntry.fluency)
                else oldEntry
            }
        )
    }

    fun addEmployment() {
        employmentHistory = employmentHistory.copy(experiences = employmentHistory.experiences + EmploymentEntry())
    }

    fun removeEmployment(index: Int) {
        employmentHistory = employmentHistory.copy(experiences = employmentHistory.experiences.filterIndexed { i, _ -> i != index })
    }

    fun updateEmployment(index: Int, updatedEntry: EmploymentEntry) {
        employmentHistory = employmentHistory.copy(
            experiences = employmentHistory.experiences.mapIndexed { i, oldEntry ->
                if (i == index) updatedEntry else oldEntry
            }
        )
    }

    fun addReference() {
        qualifications = qualifications.copy(
            references = qualifications.references + ReferenceEntry()
        )
    }

    fun removeReference(index: Int) {
        qualifications = qualifications.copy(
            references = qualifications.references.filterIndexed { i, _ -> i != index }
        )
    }

    fun updateReference(index: Int, updatedEntry: ReferenceEntry) {
        qualifications = qualifications.copy(
            references = qualifications.references.mapIndexed { i, oldEntry ->
                if (i == index) updatedEntry else oldEntry
            }
        )
    }

    fun addCertification() {
        qualifications = qualifications.copy(
            certifications = qualifications.certifications + CertificationEntry()
        )
    }

    fun removeCertification(index: Int) {
        qualifications = qualifications.copy(
            certifications = qualifications.certifications.filterIndexed { i, _ -> i != index }
        )
    }

    fun updateCertification(index: Int, updatedEntry: CertificationEntry) {
        qualifications = qualifications.copy(
            certifications = qualifications.certifications.mapIndexed { i, oldEntry ->
                if (i == index) updatedEntry else oldEntry
            }
        )
    }

    fun finalizeRegistration() {
        println("Enviando cadastro completo de ${personalInfo.firstName} para o Banco de Dados.")
    }

    // Limpeza da memória
    override fun onCleared() {
        client.close()
        super.onCleared()
    }
}