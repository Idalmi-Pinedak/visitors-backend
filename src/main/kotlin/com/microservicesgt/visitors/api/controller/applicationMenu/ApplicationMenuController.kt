package com.microservicesgt.visitors.api.controller.applicationMenu

import com.microservicesgt.visitors.domain.repository.ApplicationMenuRepository
import com.microservicesgt.visitors.domain.repository.RoleMenuRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ApplicationMenuController {

    @Autowired
    private lateinit var applicationMenuRepository: ApplicationMenuRepository

    @Autowired
    private lateinit var roleMenuRepository: RoleMenuRepository

    @Transactional(readOnly = true)
    @GetMapping(value = ["/api/application-menu/all"])
    fun findAll(): ResponseEntity<List<ApplicationMenuJsonDto>> {
        val data = applicationMenuRepository
            .findAllActiveTrue()
            .map { menu ->
                ApplicationMenuJsonDto.build {
                    this.id = menu.id
                    this.name = menu.name
                    this.icon = menu.icon
                    this.path = menu.path
                    this.parentId = menu.parentId
                    this.permissions = (menu.permissions ?: listOf()).map { permission ->
                        MenuPermissionJsonDto.build {
                            this.description = permission.description
                            this.code = permission.code
                        }
                    }
                }
            }

        return ResponseEntity.ok(data)
    }

    @Transactional(readOnly = true)
    @GetMapping(value = ["/api/role/{roleId}/menus"])
    fun findByRoleId(@PathVariable(value = "roleId") roleId: Long): ResponseEntity<List<ApplicationMenuJsonDto>> {
        val result = roleMenuRepository
            .findByRoleId(roleId)
            .map { roleMenu ->
                ApplicationMenuJsonDto.build {
                    this.id = roleMenu.applicationMenu?.id ?: 0L
                    this.name = roleMenu.applicationMenu?.name ?: ""
                    this.icon = roleMenu.applicationMenu?.icon ?: ""
                    this.path = roleMenu.applicationMenu?.path ?: ""
                    this.parentId = roleMenu.applicationMenu?.parentId ?: 0L
                    this.permissions = (roleMenu.permissions ?: listOf())
                        .map { permission ->
                            MenuPermissionJsonDto.build {
                                this.code = permission
                            }
                        }
                }
            }
            .toList()

        return ResponseEntity.ok(result)
    }

}
