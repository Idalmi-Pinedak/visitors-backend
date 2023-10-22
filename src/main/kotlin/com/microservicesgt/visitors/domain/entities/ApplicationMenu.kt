package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(schema = "application", name = "application_menu")
class ApplicationMenu : HibernateBaseEntity() {
    var name: String? = ""
    var icon: String? = ""
    var path: String? = ""
    var weight: Int? = 0

    @JdbcTypeCode(SqlTypes.JSON)
    var permissions: List<MenuPermission>? = null

    @Column(name = "parent_id")
    var parentId: Long? = null
}
