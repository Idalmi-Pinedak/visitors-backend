package com.microservicesgt.visitors.api.controller.pricing

import com.microservicesgt.visitors.domain.repository.PricingRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FindPricingController(
    private val pricingRepository: PricingRepository
) {

    @GetMapping(value = ["/api/pricing"])
    fun index(): ResponseEntity<List<PricingJsonDto>> {
        val result = pricingRepository
            .findAll()
            .sortedBy { it.weight }
            .map {
                PricingJsonDto().apply {
                    this.id = it.id
                    this.description = it.description
                    this.entranceFee = it.entranceFee
                }
            }

        return ResponseEntity.ok(result)
    }

}
