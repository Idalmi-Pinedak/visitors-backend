package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.Pricing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PricingRepository : JpaRepository<Pricing, Long> {
}
