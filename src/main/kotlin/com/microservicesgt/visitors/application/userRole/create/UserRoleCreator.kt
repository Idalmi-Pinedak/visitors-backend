package com.microservicesgt.visitors.application.userRole.create

import com.microservicesgt.visitors.application.userRole.UserRoleRequest
import com.microservicesgt.visitors.domain.entities.UserRole
import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.validators.LongValueObject
import com.microservicesgt.visitors.domain.repository.RoleRepository
import com.microservicesgt.visitors.domain.repository.UserRepository
import com.microservicesgt.visitors.domain.repository.UserRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserRoleCreator @Autowired constructor(
    private val userRoleRepository: UserRoleRepository,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {

    fun create(request: UserRoleRequest): Long {
        val userId = LongValueObject.valueOf(request.userId)
        val roleId = LongValueObject.valueOf(request.roleId)

        userId.validate()
        roleId.validate()

        deleteExistingRole(userId.value)

        val userEntity = userRepository
            .findById(userId.value)
            .orElseThrow { ValidationException("La entidad usuario no existe") }

        val roleEntity = roleRepository
            .findById(roleId.value)
            .orElseThrow { ValidationException("La entidad rol no existe") }

        val entity = UserRole().apply {
            this.user = userEntity
            this.role = roleEntity
        }

        userRoleRepository.save(entity)

        val id = LongValueObject.valueOf(entity.id)

        id.validate()

        return id.value
    }

    private fun deleteExistingRole(userId: Long) {
        val existingEntities = userRoleRepository.findByUserId(userId)

        if (existingEntities.isEmpty()) return

        userRoleRepository.deleteAll(existingEntities)
    }

}
