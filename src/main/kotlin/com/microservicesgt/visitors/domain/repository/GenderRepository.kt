package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.Gender
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GenderRepository : JpaRepository<Gender, Long> {

    fun findAllByActiveTrue(): List<Gender>

}
