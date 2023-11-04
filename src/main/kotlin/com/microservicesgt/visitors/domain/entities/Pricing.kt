package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pricing", schema = "visitor")
class Pricing : HibernateBaseEntity() {

    var description: String? = null

    @Column(name = "entrance_fee")
    var entranceFee: Double? = null

    var weight: Int? = null

}
