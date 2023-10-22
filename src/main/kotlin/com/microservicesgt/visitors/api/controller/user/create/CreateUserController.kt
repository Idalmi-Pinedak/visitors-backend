package com.microservicesgt.visitors.api.controller.user.create

import com.microservicesgt.visitors.api.controller.user.UserJsonDto
import com.microservicesgt.visitors.application.user.UserRequest
import com.microservicesgt.visitors.application.user.create.UserCreator
import com.microservicesgt.visitors.application.userRole.create.UserRoleCreator
import com.microservicesgt.visitors.application.userRole.UserRoleRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/user"])
class CreateUserController {

    @Autowired
    private lateinit var userCreator: UserCreator

    @Autowired
    private lateinit var userRoleCreator: UserRoleCreator

    @PostMapping
    @Transactional(rollbackFor = [RuntimeException::class])
    fun index(@RequestBody data: UserJsonDto): ResponseEntity<Long> {

        // 1. crea el usuario
        val request = UserRequest.build {
            this.email = data.email ?: ""
            this.name = data.name ?: ""
            this.password = data.password ?: ""
        }

        val userId = userCreator.create(request)

        // 2. Asocia el rol
        val userRoleRequest = UserRoleRequest.build {
            this.userId = userId
            this.roleId = data.roleId ?: 0L
        }

        userRoleCreator.create(userRoleRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(userId)
    }

}
