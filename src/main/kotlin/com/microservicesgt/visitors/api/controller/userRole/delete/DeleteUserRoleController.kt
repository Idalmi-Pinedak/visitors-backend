package com.microservicesgt.visitors.api.controller.userRole.delete

import com.microservicesgt.visitors.application.userRole.delete.UserRoleDeleter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/user-role"])
class DeleteUserRoleController {

    @Autowired
    private lateinit var userRoleDeleter: UserRoleDeleter

    @DeleteMapping(value = ["/{id}"])
    @Transactional(rollbackFor = [RuntimeException::class])
    fun index(@PathVariable(value = "id") id: Long): ResponseEntity<Unit> {

        userRoleDeleter.delete(id)

        return ResponseEntity.ok(Unit)
    }

}
