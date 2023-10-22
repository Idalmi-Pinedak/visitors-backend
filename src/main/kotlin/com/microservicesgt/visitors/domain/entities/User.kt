package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user", schema = "usr")
class User : HibernateBaseEntity() {
    var name: String? = ""
    var email: String? = ""
    var password: String? = ""
}
