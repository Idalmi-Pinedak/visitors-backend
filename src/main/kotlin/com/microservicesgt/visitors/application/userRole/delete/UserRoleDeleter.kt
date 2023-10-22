package com.microservicesgt.visitors.application.userRole.delete

import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.validators.LongValueObject
import com.microservicesgt.visitors.domain.repository.UserRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserRoleDeleter @Autowired constructor(private val userRoleRepository: UserRoleRepository) {
    fun delete(id: Long) {
        val userRoleId = LongValueObject.valueOf(id)
        userRoleId.validate()

        val entity = userRoleRepository
            .findById(userRoleId.value)
            .orElseThrow { ValidationException("La entidad UserRole no existe") }

        userRoleRepository.delete(entity)
    }
}
