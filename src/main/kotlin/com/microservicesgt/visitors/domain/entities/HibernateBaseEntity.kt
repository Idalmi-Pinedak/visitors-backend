package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime

@MappedSuperclass
open class HibernateBaseEntity {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @CreatedBy
    @Column(name = "created_by", length = 64)
    var createdBy: String? = ""

    @LastModifiedDate
    @Column(name = "modified_at", nullable = true)
    var modifiedAt: LocalDateTime? = LocalDateTime.now()

    @LastModifiedBy
    @Column(name = "modified_by", length = 64)
    var modifiedBy: String? = ""

    var active: Boolean = true

    @Transient
    private var authentication: Authentication? = SecurityContextHolder.getContext().authentication

    @PrePersist
    open fun persist() {
        authentication = SecurityContextHolder.getContext().authentication
        createdAt = LocalDateTime.now()
        modifiedAt = createdAt
        if (createdBy == null || "" == createdBy) {
            createdBy = authentication?.credentials?.toString() ?: "Anonimo"
        }
        authentication = null
    }

    @PreUpdate
    open fun modify() {
        authentication = SecurityContextHolder.getContext().authentication
        modifiedAt = LocalDateTime.now()
        modifiedBy = authentication?.credentials?.toString() ?: "Anonimo"
        if (modifiedBy == null || "" == modifiedBy) {
            modifiedBy = authentication?.credentials?.toString() ?: "Anonimo"
        }
        authentication = null
    }

}
