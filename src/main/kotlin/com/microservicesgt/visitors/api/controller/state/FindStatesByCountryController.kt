package com.microservicesgt.visitors.api.controller.state

import com.microservicesgt.visitors.domain.repository.StateRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/country/{countryId}/states/all"])
class FindStatesByCountryController(
    private val stateRepository: StateRepository
) {

    @GetMapping
    fun findByCountry(@PathVariable countryId: Long): ResponseEntity<List<StateJsonDto>> {
        val result = stateRepository
            .findByCountryIdAndActiveTrue(countryId)
            .map {
                StateJsonDto().apply {
                    this.id = it.id
                    this.stateName = it.stateName
                }
            }

        return ResponseEntity.ok(result)
    }

}
