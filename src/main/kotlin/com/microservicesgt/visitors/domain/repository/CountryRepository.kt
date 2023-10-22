package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long> {

    @Query(value = "from Country as t0 where t0.active = true order by t0.id asc")
    fun findAllByActiveTrue(): List<Country>

}
