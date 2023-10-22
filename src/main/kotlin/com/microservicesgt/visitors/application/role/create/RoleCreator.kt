package com.microservicesgt.visitors.application.role.create

import com.microservicesgt.visitors.application.role.RoleRequest
import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.validators.LongValueObject
import com.microservicesgt.visitors.domain.validators.StringValueObject
import com.microservicesgt.visitors.domain.entities.Role
import com.microservicesgt.visitors.domain.entities.RoleMenu
import com.microservicesgt.visitors.domain.repository.ApplicationMenuRepository
import com.microservicesgt.visitors.domain.repository.RoleRepository
import com.microservicesgt.visitors.domain.repository.RoleMenuRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RoleCreator @Autowired constructor(
    private val roleRepository: RoleRepository,
    private val roleMenuRepository: RoleMenuRepository,
    private val applicationMenuRepository: ApplicationMenuRepository
) {

    fun create(request: RoleRequest): Long {
        val roleCode = StringValueObject.valueOf(request.code)
        val roleName = StringValueObject.valueOf(request.name)

        roleCode.validate()
        roleName.validate()

        val otherRole = roleRepository.findByCode(roleCode.value)

        // 1. Validamos si existe un rol con el mismo codigo lanzamos un error
        if (otherRole != null) {
            throw ValidationException("Ya existe un rol con el codigo ${roleCode.value}")
        }

        val roleEntity = Role().apply {
            this.code = roleCode.value
            this.name = roleName.value
        }

        // 2. Guarda el base de datos el nuevo rol
        roleRepository.save(roleEntity)

        val roleId = LongValueObject.valueOf(roleEntity.id)
        roleId.validate()

        // 3. Asocia al nuevo rol los menus a los que tendra acceso
        val menus = request
            .menuList
            .map { menu ->
                val applicationMenuEntity = applicationMenuRepository
                    .findById(menu.menuId)
                    .orElseThrow { ValidationException("La entidad Menu no existe") }

                return@map RoleMenu().apply {
                    this.applicationMenu = applicationMenuEntity
                    this.role = roleEntity
                    this.permissions = menu.permissions
                }
            }
            .toList()

        roleMenuRepository.saveAll(menus)

        return roleId.value
    }

}
