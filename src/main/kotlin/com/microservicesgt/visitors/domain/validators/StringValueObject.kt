package com.microservicesgt.visitors.domain.validators

import com.microservicesgt.visitors.domain.exceptions.ValidationException

class StringValueObject(val value: String = "", private val errorMessage: String) {
    fun validate() {
        if (value.isEmpty()) {
            throw ValidationException(errorMessage)
        }
    }

    companion object {
        fun valueOf(
            value: String?,
            errorMessage: String = "El campo es requerido"
        ) = StringValueObject(value ?: "", errorMessage)
    }
}
