package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StateRepository : JpaRepository<State, Long> {

    @Query(value = "from State as t0 where t0.active and t0.country.id = :countryId order by t0.id asc")
    fun findByCountryIdAndActiveTrue(@Param(value = "countryId") countryId: Long): List<State>

}
