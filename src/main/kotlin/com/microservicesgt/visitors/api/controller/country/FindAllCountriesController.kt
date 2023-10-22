package com.microservicesgt.visitors.api.controller.country

import com.microservicesgt.visitors.domain.repository.CountryRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/country/all"])
class FindAllCountriesController(
    private val countryRepository: CountryRepository
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<CountryJsonDto>> {
        val result = countryRepository
            .findAllByActiveTrue()
            .map {
                CountryJsonDto().apply {
                    this.id = it.id
                    this.countryName = it.countryName
                }
            }

        return ResponseEntity.ok(result)
    }

}
