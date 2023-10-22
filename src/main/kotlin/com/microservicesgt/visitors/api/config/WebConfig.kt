package com.microservicesgt.visitors.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedHeaders("Content-Type", "Authorization", "authorization")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            }
        }
    }

}
