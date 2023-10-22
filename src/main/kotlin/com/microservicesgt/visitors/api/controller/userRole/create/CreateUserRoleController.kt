package com.microservicesgt.visitors.api.controller.userRole.create

import com.microservicesgt.visitors.api.controller.userRole.UserRoleJSONDTO
import com.microservicesgt.visitors.application.userRole.UserRoleRequest
import com.microservicesgt.visitors.application.userRole.create.UserRoleCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/user-role"])
class CreateUserRoleController {

    @Autowired
    private lateinit var userRoleCreator: UserRoleCreator

    @PostMapping
    @Transactional(rollbackFor = [RuntimeException::class])
    fun index(@RequestBody data: UserRoleJSONDTO): ResponseEntity<Long> {
        val request = UserRoleRequest.build {
            this.userId = data.userId ?: 0L
            this.roleId = data.roleId ?: 0L
        }

        val result = userRoleCreator.create(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

}
