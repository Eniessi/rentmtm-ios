package com.rentmtm.mapper


import com.rentmtm.model.Address
import com.rentmtm.db.AddressEntity

fun AddressEntity.toDomainModel(): Address {
    return Address(
        id = this.idAddress.toInt(),
        street = this.street,
        city = this.city,
        state = this.state,
        country = this.country,
        zipCode = this.zipCode
    )
}

fun Address.toEntity(): AddressEntity {
    return AddressEntity(
        idAddress = this.id.toLong(),
        street = this.street,
        city = this.city,
        state = this.state,
        country = this.country,
        zipCode = this.zipCode
    )
}