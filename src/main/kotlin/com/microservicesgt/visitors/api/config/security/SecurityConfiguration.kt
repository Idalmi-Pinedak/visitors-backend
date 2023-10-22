package com.microservicesgt.visitors.api.config.security

import com.microservicesgt.visitors.api.config.filters.JWTAuthorizationFilter
import com.microservicesgt.visitors.application.utils.JwtUtil
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfiguration {

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun filterChain(
        http: HttpSecurity?,
        authenticationManager: AuthenticationManager,
        jwtUtil: JwtUtil
    ): SecurityFilterChain {
//        http!!.cors().and().csrf().disable()
//            .authorizeHttpRequests { authorize ->
//                authorize
//                    .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
//                    .anyRequest()
//                    .authenticated()
//                    .and()
//                    .exceptionHandling()
//                    .authenticationEntryPoint(unauthorizedAccess())
//            }
//            .addFilter(JWTAuthorizationFilter())
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http!!.cors().and().csrf().disable().authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/reports/visitor-group/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedAccess())
            .and()
            .addFilter(
                JWTAuthorizationFilter(
                    jwtUtil,
                    authenticationManager
                )
            )
        // this disables session creation on Spring Security

        return http.build()
    }

    fun unauthorizedAccess(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { _, httpServletResponse, _ ->
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }

}
