package com.microservicesgt.visitors.api.controller.role.update

import com.microservicesgt.visitors.api.controller.role.RoleJson
import com.microservicesgt.visitors.application.role.RoleMenuRequest
import com.microservicesgt.visitors.application.role.RoleRequest
import com.microservicesgt.visitors.application.role.update.RoleUpdater
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateRoleController {

    @Autowired
    private lateinit var roleUpdater: RoleUpdater

    @PutMapping(value = ["/api/role"])
    @Transactional
    fun index(@RequestBody data: RoleJson): ResponseEntity<Unit> {
        val roleRequest = RoleRequest.build {
            this.id = data.id ?: 0L
            this.name = data.name ?: ""
            this.code = data.code ?: ""
            this.active = data.active ?: false
            this.menuList = data.menuList?.map {
                RoleMenuRequest().apply {
                    this.menuId = it.menuId ?: 0L
                    this.permissions = it.permissions ?: listOf()
                }
            } ?: listOf()
        }

        roleUpdater.update(roleRequest)

        return ResponseEntity.ok(Unit)
    }

}
