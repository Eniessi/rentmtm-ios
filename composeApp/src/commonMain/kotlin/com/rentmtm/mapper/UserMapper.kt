package com.rentmtm.mapper

import com.rentmtm.model.*
import com.rentmtm.model.enums.*
import com.rentmtm.db.UserEntity

fun UserEntity.toDomainModel(
    userType: UserType? = null,
    rentProfile: RentProfile? = null,
    marketplaceProfile: MarketplaceProfile? = null
): User {
    return User(
        id = this.id,
        userType = userType,
        rentProfile = rentProfile,
        marketplaceProfile = marketplaceProfile,
        addressId = this.addressId,
        profile = this.profile,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        contractType = this.contractType,
        accessKey = this.accessKey,
        password = this.password,
        phoneNumber = this.phoneNumber,
        city = this.city,
        state = this.state,
        fullNameHolder = this.fullNameHolder,
        typeDocument = this.typeDocument,
        documentNumber = this.documentNumber,
        documentDetails = this.documentDetails,
        dateBirth = this.dateBirth,
        expirationDate = this.expirationDate,
        issueDate = this.issueDate,
        loginDateTime = this.loginDateTime,
        isSubscriber = this.isSubscriber,
        isConnected = this.isConnected,
        financialStatus = this.financialStatus,
        // Conversão segura de Enums
        gender = this.gender?.let { Gender.valueOf(it) },
        typeDocumentEnum = this.typeDocumentEnum?.let { TypeDocument.valueOf(it) },
        validationMethod = this.validationMethod?.let { ValidationMethod.valueOf(it) }
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        userTypeId = this.userType?.id?.toLong(),
        rentProfileId = this.rentProfile?.id,
        marketplaceProfileId = this.marketplaceProfile?.id,
        addressId = this.addressId,
        profile = this.profile,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        contractType = this.contractType,
        accessKey = this.accessKey,
        password = this.password,
        phoneNumber = this.phoneNumber,
        city = this.city,
        state = this.state,
        fullNameHolder = this.fullNameHolder,
        typeDocument = this.typeDocument,
        documentNumber = this.documentNumber,
        documentDetails = this.documentDetails,
        dateBirth = this.dateBirth,
        expirationDate = this.expirationDate,
        issueDate = this.issueDate,
        loginDateTime = this.loginDateTime,
        isSubscriber = this.isSubscriber,
        isConnected = this.isConnected,
        financialStatus = this.financialStatus,
        gender = this.gender?.name,
        typeDocumentEnum = this.typeDocumentEnum?.name,
        validationMethod = this.validationMethod?.name
    )
}