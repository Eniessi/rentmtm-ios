package com.rentmtm.mapper

import com.rentmtm.model.Language
import com.rentmtm.model.enums.LanguageFluency
import com.rentmtm.db.Language as DbLanguage

fun DbLanguage.toDomainModel(): Language {
    return Language(
        id = this.id.toInt(),
        name = this.name,
        fluencyLevel = this.fluencyLevel?.let { enumValueOf<LanguageFluency>(it) }
    )
}

fun Language.toEntity(): DbLanguage {
    return DbLanguage(
        id = this.id.toLong(),
        name = this.name,
        fluencyLevel = this.fluencyLevel?.name
    )
}