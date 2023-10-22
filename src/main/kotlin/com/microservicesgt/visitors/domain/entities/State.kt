package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "state", schema = "visitor")
class State : HibernateBaseEntity() {

    var stateName: String? = ""

    @ManyToOne(fetch = FetchType.LAZY)
    var country: Country? = null

}
