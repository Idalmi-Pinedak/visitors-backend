package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.VisitorGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VisitorGroupRepository : JpaRepository<VisitorGroup, Long> {
}
