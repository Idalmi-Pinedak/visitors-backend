package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query(
        value = " from User as t0 " +
                " where t0.email = :email "
    )
    fun findByEmail(@Param(value = "email") email: String): User?

}
