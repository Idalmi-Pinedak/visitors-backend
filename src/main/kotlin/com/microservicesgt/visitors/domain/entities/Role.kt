package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "role", schema = "usr")
class Role : HibernateBaseEntity() {
    var code: String? = ""
    var name: String? = ""
}
