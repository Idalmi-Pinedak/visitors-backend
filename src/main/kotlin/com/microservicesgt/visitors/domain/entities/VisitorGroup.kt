package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "visitor_group", schema = "visitor")
class VisitorGroup : HibernateBaseEntity() {

    @Column(name = "check_in_date")
    var checkInDate: LocalDateTime? = null

    @Column(name = "check_out_date")
    var checkOutDate: LocalDateTime? = null

    @Column(name = "total_visitors")
    var totalVisitors: Int = 0

    @Column(name = "total_amount")
    var totalAmount: Double = 0.0

}
