package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "country", schema = "visitor")
class Country : HibernateBaseEntity() {

    var countryName: String? = ""

}

