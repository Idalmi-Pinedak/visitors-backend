package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.RoleMenu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RoleMenuRepository : JpaRepository<RoleMenu, Long> {

    @Query(
        value = "from RoleMenu " +
                " where role.id = :roleId " +
                " order by applicationMenu.weight "
    )
    fun findByRoleId(@Param(value = "roleId") roleId: Long): List<RoleMenu>

    @Modifying
    @Query(value = "delete from RoleMenu where id in(select t0.id from RoleMenu as t0 where t0.role.id = :roleId)")
    fun deleteByRoleId(@Param(value = "roleId") roleId: Long): Int

}
