package com.microservicesgt.visitors.api.config.security

import io.jsonwebtoken.Claims
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class AuthenticatedUserToken(private val claims: Claims) : Authentication {
    val id: Long = claims["userId"].toString().toLong()

    val email: String = claims.subject

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.isAuthenticated = isAuthenticated
    }

    override fun getName(): String {
        return claims["name"].toString()
    }

    override fun getCredentials(): Any {
        return claims.subject
    }

    override fun getPrincipal(): Any {
        return claims["userId"].toString().toLong()
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun getDetails(): Any {
        return claims
    }

    override fun toString(): String {
        return "Authentication(id=$id, email=$email)"
    }

}
