package com.rentmtm.mapper

import com.rentmtm.model.*
import com.rentmtm.model.User // O seu Model de Domínio
import com.rentmtm.db.User as DbUser // A Tabela do Banco renomeada via Alias para evitar conflito

object UserMapper {

    // ==========================================
    // LEITURA (Banco de Dados -> Kotlin Model)
    // ==========================================
    fun toDomain(
        dbUser: DbUser, // ⬅️ Usando o Alias aqui!
        rentProfile: RentProfile? = null,
        marketplaceProfile: MarketplaceProfile? = null,
        profProfile: ProfAllocatedProfile? = null,
        paymentMethods: List<PaymentMethod> = emptyList(),
        sentBudgets: List<Budget> = emptyList(),
        receivedBudgets: List<Budget> = emptyList(),
        myServiceOrders: List<ServiceOrder> = emptyList(),
        financialStatement: List<Movimentation> = emptyList()
    ): User {
        return User(
            id = dbUser.id,
            userType = dbUser.userType,
            firstName = dbUser.firstName,
            lastName = dbUser.lastName,
            email = dbUser.email,
            password = dbUser.password,
            phoneNumber = dbUser.phoneNumber,
            dateBirth = dbUser.dateBirth,
            gender = dbUser.gender,
            nationality = dbUser.nationality,
            primaryLanguage = dbUser.primaryLanguage,
            fullNameHolder = dbUser.fullNameHolder,
            documentNumber = dbUser.documentNumber,
            documentDetails = dbUser.documentDetails,
            licenseClass = dbUser.licenseClass,
            passportNumber = dbUser.passportNumber,
            issuingCountry = dbUser.issuingCountry,
            ssn = dbUser.ssn,
            issueDate = dbUser.issueDate,
            expirationDate = dbUser.expirationDate,
            addressId = dbUser.addressId.toInt(),
            city = dbUser.city,
            state = dbUser.state,
            profile = dbUser.profile,
            contractType = dbUser.contractType,
            accessKey = dbUser.accessKey,
            loginDateTime = dbUser.loginDateTime,
            idFrontPath = dbUser.idFrontPath,
            idBackPath = dbUser.idBackPath,
            selfiePath = dbUser.selfiePath,
            isSubscriber = dbUser.isSubscriber,
            isConnected = dbUser.isConnected,
            financialStatus = dbUser.financialStatus,

            // Composição e Listas
            rentProfile = rentProfile,
            marketplaceProfile = marketplaceProfile,
            profAllocatedProfile = profProfile,
            paymentMethods = paymentMethods,
            sentBudgets = sentBudgets,
            receivedBudgets = receivedBudgets,
            myServiceOrders = myServiceOrders,
            financialStatement = financialStatement
        )
    }

    // ==========================================
    // ESCRITA (Kotlin Model -> Banco de Dados)
    // ==========================================
    fun toDbUser(
        domain: User,
        savedAddressId: Long,
        savedRentProfileId: Long? = null,
        savedMarketplaceProfileId: Long? = null,
        savedProfProfileId: Long? = null
    ): DbUser { // ⬅️ Devolvendo o tipo gerado pelo SQLDelight
        return DbUser(
            id = domain.id,
            userType = domain.userType,
            firstName = domain.firstName,
            lastName = domain.lastName,
            email = domain.email,
            password = domain.password,
            phoneNumber = domain.phoneNumber,
            dateBirth = domain.dateBirth,
            gender = domain.gender,
            nationality = domain.nationality,
            primaryLanguage = domain.primaryLanguage,
            fullNameHolder = domain.fullNameHolder,
            documentNumber = domain.documentNumber,
            documentDetails = domain.documentDetails,
            licenseClass = domain.licenseClass,
            passportNumber = domain.passportNumber,
            issuingCountry = domain.issuingCountry,
            ssn = domain.ssn,
            issueDate = domain.issueDate,
            expirationDate = domain.expirationDate,
            city = domain.city,
            state = domain.state,
            profile = domain.profile,
            contractType = domain.contractType,
            accessKey = domain.accessKey,
            loginDateTime = domain.loginDateTime,
            idFrontPath = domain.idFrontPath,
            idBackPath = domain.idBackPath,
            selfiePath = domain.selfiePath,
            isSubscriber = domain.isSubscriber,
            isConnected = domain.isConnected,
            financialStatus = domain.financialStatus,

            // Chaves Estrangeiras (Foreign Keys)
            addressId = savedAddressId,
            rentProfileId = savedRentProfileId,
            marketplaceProfileId = savedMarketplaceProfileId,
            profAllocatedProfileId = savedProfProfileId
        )
    }
}