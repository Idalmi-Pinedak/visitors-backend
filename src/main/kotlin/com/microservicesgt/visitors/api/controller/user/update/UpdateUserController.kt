package com.microservicesgt.visitors.api.controller.user.update

import com.microservicesgt.visitors.api.controller.user.UserJsonDto
import com.microservicesgt.visitors.application.user.UserRequest
import com.microservicesgt.visitors.application.user.update.UserUpdater
import com.microservicesgt.visitors.application.userRole.UserRoleRequest
import com.microservicesgt.visitors.application.userRole.create.UserRoleCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
@RequestMapping(value = ["/api/user"])
class UpdateUserController {

    @Autowired
    private lateinit var userUpdater: UserUpdater

    @Autowired
    private lateinit var userRoleCreator: UserRoleCreator

    @PutMapping
    @Transactional(rollbackFor = [RuntimeException::class])
    fun index(
        @RequestBody data: UserJsonDto
    ): ResponseEntity<Unit> {
        // 1. actualiza el usuario
        val request = UserRequest.build {
            this.userId = data.id ?: 0L
            this.email = data.email ?: ""
            this.name = data.name ?: ""
            this.password = data.password ?: ""
        }

        userUpdater.update(request)

        // 2. Asocia el rol
        val userRoleRequest = UserRoleRequest.build {
            this.userId = data.id ?: 0L
            this.roleId = data.roleId ?: 0L
        }

        userRoleCreator.create(userRoleRequest)

        return ResponseEntity.ok(Unit)
    }

    @PutMapping(value = ["/reset-password/{userId}"])
    @Transactional(rollbackFor = [RuntimeException::class])
    fun resetPassword(@PathVariable(value = "userId") userId: Long): ResponseEntity<Unit> {
        userUpdater.resetPassword(userId)
        return ResponseEntity.ok(Unit)
    }

    @PutMapping(value = ["/delete/{userId}"])
    @Transactional(rollbackFor = [RuntimeException::class])
    fun deactivate(@PathVariable(value = "userId") userId: Long): ResponseEntity<Unit> {
        userUpdater.deactivate(userId)
        return ResponseEntity.ok(Unit)
    }

}
