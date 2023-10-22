package com.microservicesgt.visitors.application.role.update

import com.microservicesgt.visitors.application.role.RoleRequest
import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.validators.LongValueObject
import com.microservicesgt.visitors.domain.validators.StringValueObject
import com.microservicesgt.visitors.domain.entities.RoleMenu
import com.microservicesgt.visitors.domain.repository.ApplicationMenuRepository
import com.microservicesgt.visitors.domain.repository.RoleRepository
import com.microservicesgt.visitors.domain.repository.RoleMenuRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RoleUpdater @Autowired constructor(
    private val roleRepository: RoleRepository,
    private val roleMenuRepository: RoleMenuRepository,
    private val applicationMenuRepository: ApplicationMenuRepository
) {

    fun update(request: RoleRequest) {
        // 1. Valida los datos obligatorios
        val roleId = LongValueObject.valueOf(request.id)
        val roleCode = StringValueObject.valueOf(request.code)
        val roleName = StringValueObject.valueOf(request.name)

        roleId.validate()
        roleCode.validate()
        roleName.validate()

        // 2. Valida que exista el rol que se quiere modificar
        val roleEntity = roleRepository
            .findById(roleId.value)
            .orElseThrow { ValidationException("El Rol a modificar no existe") }

        // 3. Modifica los campos
        roleEntity.apply {
            this.name = roleName.value
            this.code = roleCode.value
            this.active = request.active
        }

        // 4. Guarda cambios
        roleRepository.save(roleEntity)

        // 5. Actualia los menus a los que tiene acceso el rol
        if (request.menuList.isNotEmpty()) {
//            println("obteniendo los menus anteriores")

//            val roleMenuEntities = roleMenuRepository.findByRoleId(roleId.value)
//            println("Se obtuvieron los menus anteriores exitosamente")

//            roleMenuRepository.deleteAll(roleMenuEntities)
            roleMenuRepository.deleteByRoleId(roleId.value)

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
        }
    }

}
