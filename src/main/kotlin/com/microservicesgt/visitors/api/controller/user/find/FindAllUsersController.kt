package com.microservicesgt.visitors.api.controller.user.find

import com.microservicesgt.visitors.api.controller.user.UserJsonDto
import com.microservicesgt.visitors.domain.repository.UserRepository
import com.microservicesgt.visitors.domain.repository.UserRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/user"])
class FindAllUsersController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userRoleRepository: UserRoleRepository

    @GetMapping
    fun getAll(): ResponseEntity<List<UserJsonDto>> {
        val users = userRepository
            .findAll()
            .map {
                UserJsonDto.build {
                    this.id = it.id
                    this.name = it.name
                    this.email = it.email
                    this.active = it.active
                    this.createdAt = it.createdAt
                    this.createdBy = it.createdBy
                    this.modifiedAt = it.modifiedAt
                    this.modifiedBy = it.modifiedBy
                    this.roleId = getRoleId(it.id ?: 0L)
                }
            }

        return ResponseEntity.ok(users)
    }

    private fun getRoleId(userId: Long): Long {
        val roles = userRoleRepository.findByUserId(userId)

        if (roles.isEmpty()) return 0L

        return roles[0].role?.id ?: 0L
    }

}
