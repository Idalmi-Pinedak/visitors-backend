package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "visitor", schema = "visitor")
class Visitor : HibernateBaseEntity() {

    @Column(name = "visitor_name")
    var visitorName: String? = null

    var age: Short? = null

    @Column(name = "entrance_fee")
    var entranceFee: Double? = null

    @Column(name = "gender_id")
    var genderId: Long? = null

    @Column(name = "country_id")
    var countryId: Long? = null

    @Column(name = "state_id")
    var stateId: Long? = null

    @Column(name = "visitor_group_id")
    var visitorGroupId: Long? = null

    @Column(name = "check_in_date")
    var checkInDate: LocalDateTime? = null

    @Column(name = "check_out_date")
    var checkOutDate: LocalDateTime? = null

}
