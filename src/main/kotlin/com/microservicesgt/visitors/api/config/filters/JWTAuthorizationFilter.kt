package com.microservicesgt.visitors.api.config.filters

import com.microservicesgt.visitors.api.config.security.AuthenticatedUserToken
import com.microservicesgt.visitors.application.utils.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class JWTAuthorizationFilter(private val jwtUtil: JwtUtil, authenticationManager: AuthenticationManager) :
    BasicAuthenticationFilter(authenticationManager) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val tokenPrefix = "Bearer"
    private val headerString = "Authorization"
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val header = request.getHeader(headerString)

            if (header != null && header.startsWith(tokenPrefix)) {
                val accessToken = header.replace(tokenPrefix, "").trim()

                val decodedToken = jwtUtil.getTokenClaims(accessToken)

                val authentication = AuthenticatedUserToken(decodedToken)

                SecurityContextHolder.getContext().authentication = authentication
            }

        } catch (ex: Exception) {
            log.error("ERROR AL VERIFICAR TOKEN {}", ex.message)
        }

        filterChain.doFilter(request, response)
    }

}
