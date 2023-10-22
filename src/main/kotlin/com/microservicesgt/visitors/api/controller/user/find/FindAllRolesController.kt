package com.microservicesgt.visitors.api.controller.user.find

import com.microservicesgt.visitors.api.controller.role.RoleJson
import com.microservicesgt.visitors.domain.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/role/all"])
class FindAllRolesController {

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Transactional(readOnly = true)
    @GetMapping
    fun index(): ResponseEntity<List<RoleJson>> {
        val data = roleRepository
            .findAll()
            .map { row ->
                RoleJson.build {
                    this.id = row.id
                    this.name = row.name
                    this.code = row.code
                    this.active = row.active
                }
            }

        return ResponseEntity.ok(data)
    }

}