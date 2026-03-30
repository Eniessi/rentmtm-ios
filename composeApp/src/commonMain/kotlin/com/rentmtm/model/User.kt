package com.rentmtm.model

import com.rentmtm.model.enums.Gender
import com.rentmtm.model.enums.TypeDocument
import com.rentmtm.model.enums.ValidationMethod
import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: Long = 0L,
    var userType: UserType? = null,


    // PERFIS
    var rentProfile: RentProfile? = null,
    var marketplaceProfile: MarketplaceProfile? = null,
    var profAllocatedProfile: ProfAllocatedProfile? = null, // ⬅️ O Crachá Profissional

    // ==========================================
    // DADOS DO HUMANO (Centralizados)
    // ==========================================
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    //var contractType: String? = null,
    //var accessKey: String? = null,
    var paymentMethods: List<PaymentMethod> = emptyList(),
    var password: String? = null,
    var phoneNumber: String? = null,
    var dateBirth: String? = null,
    var gender: Gender? = null,
    var nationality: String? = null,
    var primaryLanguage: String? = null,
    var emergencyContact: EmergencyContact? = null,

    // Documentos da Pessoa
    var fullNameHolder: String? = null,
    var documentNumber: String? = null,
    var documentDetails: String? = null,
    var licenseClass: String? = null,
    var passportNumber: String? = null,
    var issuingCountry: String? = null,
    var ssn: String? = null,
    var issueDate: String? = null,
    var expirationDate: String? = null,

    // Relacionamento de Endereço Residencial
    var addressId: Int = 0,
    var city: String? = null,  // (Pode comentar isto se preferir normalizar pelo Address)
    var state: String? = null, // (Pode comentar isto se preferir normalizar pelo Address)

    // Dados de Controle do Sistema
    var profile: String? = null,
    var contractType: String? = null,
    var accessKey: String? = null,
    var loginDateTime: String? = null,
    var isSubscriber: Boolean = false,
    var isConnected: Boolean = false,
    var financialStatus: Boolean = false,

    var idFrontPath: String? = null,
    var idBackPath: String? = null,
    var selfiePath: String? = null,

    // novos atributos de novos models
    var sentBudgets: List<Budget> = emptyList(),     // Orçamentos que ele pediu (como cliente)
    var receivedBudgets: List<Budget> = emptyList(),

    var myServiceOrders: List<ServiceOrder> = emptyList(),

    var financialStatement: List<Movimentation> = emptyList()

)