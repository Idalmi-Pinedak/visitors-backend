package com.microservicesgt.visitors.api.controller.role.create

import com.microservicesgt.visitors.api.controller.role.RoleJson
import com.microservicesgt.visitors.application.role.RoleMenuRequest
import com.microservicesgt.visitors.application.role.RoleRequest
import com.microservicesgt.visitors.application.role.create.RoleCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateRoleController {

    @Autowired
    private lateinit var roleCreator: RoleCreator

    @PostMapping(value = ["/api/role"])
    fun index(@RequestBody data: RoleJson): ResponseEntity<Long> {
        val roleRequest = RoleRequest.build {
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

        val id = roleCreator.create(roleRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(id)
    }

}
