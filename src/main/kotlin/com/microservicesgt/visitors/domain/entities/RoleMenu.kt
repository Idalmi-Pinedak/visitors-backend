package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(schema = "application", name = "role_menu")
class RoleMenu : HibernateBaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_menu_id")
    var applicationMenu: ApplicationMenu? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    var role: Role? = null

    @JdbcTypeCode(SqlTypes.JSON)
    var permissions: List<String>? = null
}
