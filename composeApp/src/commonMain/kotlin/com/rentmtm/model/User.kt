package com.rentmtm.model

import com.rentmtm.model.enums.Gender
import com.rentmtm.model.enums.TypeDocument
import com.rentmtm.model.enums.ValidationMethod
import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: Long = 0L,
    var userType: UserType? = null,
    var rentProfile: RentProfile? = null,
    var marketplaceProfile: MarketplaceProfile? = null,
    var addressId: Int = 0,
    var profile: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var contractType: String? = null,
    var accessKey: String? = null,
    var password: String? = null,
    var phoneNumber: String? = null,
    var city: String? = null,
    var state: String? = null,
    var fullNameHolder: String? = null,
    var typeDocument: String? = null,
    var documentNumber: String? = null,
    var documentDetails: String? = null,
    var dateBirth: String? = null,
    var expirationDate: String? = null,
    var issueDate: String? = null,
    var loginDateTime: String? = null,
    var isSubscriber: Boolean = false,
    var isConnected: Boolean = false,
    var financialStatus: Boolean = false,
    var gender: Gender? = null,
    var typeDocumentEnum: TypeDocument? = null,
    var validationMethod: ValidationMethod? = null
)