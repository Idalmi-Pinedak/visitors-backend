package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "gender", schema = "visitor")
class Gender : HibernateBaseEntity() {

    var description: String? = null

}
