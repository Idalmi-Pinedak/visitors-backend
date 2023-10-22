package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_role", schema = "usr")
class UserRole : HibernateBaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var role: Role? = null

}
