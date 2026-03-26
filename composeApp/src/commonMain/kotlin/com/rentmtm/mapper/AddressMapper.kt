package com.rentmtm.mapper

import com.rentmtm.model.Address
import com.rentmtm.db.Address as DbAddress

object AddressMapper {
    fun toDomain(dbAddress: DbAddress): Address {
        return Address(
            id = dbAddress.id.toInt(),
            street = dbAddress.street,
            city = dbAddress.city,
            state = dbAddress.state,
            country = dbAddress.country,
            zipCode = dbAddress.zipCode,
            number = dbAddress.number?.toInt(),
            apartment = dbAddress.apartment
        )
    }

    fun toDbAddress(domain: Address): DbAddress {
        return DbAddress(
            id = domain.id.toLong(),
            street = domain.street,
            city = domain.city,
            state = domain.state,
            country = domain.country,
            zipCode = domain.zipCode,
            number = domain.number?.toLong(),
            apartment = domain.apartment
        )
    }
}