package com.microservicesgt.visitors.domain.validators

import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.shared.DomainConstants

class EmailValueObject(val value: String) {
    fun validate() {
        if (value.isEmpty()) {
            throw ValidationException("El email es requerido")
        }

        if (!value.matches(DomainConstants.EMAIL_REGEX.toRegex())) {
            throw ValidationException("El email no tiene el formato correcto")
        }
    }

    companion object {
        fun valueOf(value: String?) = EmailValueObject(value ?: "")
    }
}