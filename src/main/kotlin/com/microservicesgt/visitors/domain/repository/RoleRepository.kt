package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: JpaRepository<Role, Long> {

    fun findByCode(code: String): Role?

}
