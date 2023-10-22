package com.microservicesgt.visitors.domain.validators

import com.microservicesgt.visitors.domain.exceptions.ValidationException

class LongValueObject(val value: Long, private val errorMessage: String) {

    fun validate() {
        if (value <= 0L) {
            throw ValidationException(errorMessage)
        }
    }

    companion object {
        fun valueOf(value: Long?) = LongValueObject(value ?: 0L, "El campo id es requerido")
    }

}
