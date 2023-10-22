package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRoleRepository : JpaRepository<UserRole, Long> {

    @Query(
        value = " from UserRole as t0 " +
                " where t0.user.id = :userId " +
                " and t0.active = true " +
                " order by t0.role.name "
    )
    fun findByUserId(@Param(value = "userId") userId: Long): List<UserRole>

    @Query(
        value = " from UserRole as t0 " +
                " where t0.user.id = :userId " +
                " and t0.role.id = :roleId " +
                " and t0.active = true "
    )
    fun findByUserIdAndRoleIdAndActiveTrue(
        @Param(value = "userId") userId: Long,
        @Param(value = "roleId") roleId: Long
    ): UserRole?

}
