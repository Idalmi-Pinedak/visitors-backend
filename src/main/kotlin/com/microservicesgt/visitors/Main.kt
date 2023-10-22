package com.microservicesgt.visitors

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.microservicesgt.visitors.domain.repository"])
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
