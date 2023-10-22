package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.ApplicationMenu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ApplicationMenuRepository : JpaRepository<ApplicationMenu, Long> {

    @Query(value = "from ApplicationMenu  as t0 " +
            "where t0.active = true " +
            "order by t0.weight")
    fun findAllActiveTrue(): List<ApplicationMenu>

}
