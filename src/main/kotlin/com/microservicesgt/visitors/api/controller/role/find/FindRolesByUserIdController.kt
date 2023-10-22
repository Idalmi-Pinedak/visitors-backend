package com.microservicesgt.visitors.api.controller.role.find

import com.microservicesgt.visitors.api.controller.role.RoleJson
import com.microservicesgt.visitors.domain.repository.UserRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FindRolesByUserIdController {

    @Autowired
    private lateinit var userRoleRepository: UserRoleRepository

    @GetMapping(value = ["/api/user/{userId}/roles"])
    fun index(@PathVariable(value = "userId") userId: Long): ResponseEntity<List<RoleJson>> {
        val roles = userRoleRepository
            .findByUserId(userId)
            .map { userRole ->
                RoleJson.build {
                    this.id = userRole.role?.id
                    this.name = userRole.role?.name
                    this.code = userRole.role?.code
                    this.active = userRole.role?.active
                }
            }

        return ResponseEntity.ok(roles)
    }

}
