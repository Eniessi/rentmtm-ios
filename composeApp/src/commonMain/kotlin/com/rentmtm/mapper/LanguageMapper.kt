package com.rentmtm.mapper

import com.rentmtm.model.Language
import com.rentmtm.model.enums.LanguageFluency
import com.rentmtm.db.LanguageEntity

fun LanguageEntity.toDomainModel(): Language {
    return Language(
        id = this.idLanguage.toInt(),
        name = this.name,
        fluencyLevel = this.fluencyLevel?.let { enumValueOf<LanguageFluency>(it) }
    )
}

fun Language.toEntity(): LanguageEntity {
    return LanguageEntity(
        idLanguage = this.id.toLong(),
        name = this.name,
        fluencyLevel = this.fluencyLevel?.name
    )
}