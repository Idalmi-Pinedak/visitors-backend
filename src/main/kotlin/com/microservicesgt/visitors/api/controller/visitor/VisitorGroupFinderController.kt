package com.microservicesgt.visitors.api.controller.visitor

import com.microservicesgt.visitors.api.controller.visitor.dto.VisitorGroupJsonDto
import com.microservicesgt.visitors.api.controller.visitor.dto.VisitorJsonDto
import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.repository.VisitorGroupRepository
import com.microservicesgt.visitors.domain.repository.VisitorRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class VisitorGroupFinderController(
    private val visitorGroupRepository: VisitorGroupRepository,
    private val visitorRepository: VisitorRepository
) {

    @GetMapping(value = ["/api/visitor-group/{visitorGroupId}"])
    fun find(@PathVariable visitorGroupId: Long): ResponseEntity<VisitorGroupJsonDto> {
        val visitorGroup = visitorGroupRepository
            .findById(visitorGroupId)
            .orElseThrow { ValidationException("Visitor group not exists") }

        val visitors = visitorRepository
            .findByVisitorGroupId(visitorGroup.id ?: 0L)

        val response = VisitorGroupJsonDto().apply {
            this.id = visitorGroup.id
            this.checkInDate = visitorGroup.checkInDate
            this.checkOutDate = visitorGroup.checkOutDate
            this.totalVisitors = visitorGroup.totalVisitors
            this.totalAmount = visitorGroup.totalAmount
            this.visitors = visitors.map {
                VisitorJsonDto().apply {
                    this.visitorName = it.visitorName
                    this.age = it.age
                    this.genderId = it.genderId
                    this.countryId = it.countryId
                    this.stateId = it.stateId
                    this.entranceFee = it.entranceFee
                    this.checkInDate = it.checkInDate
                    this.checkOutDate = it.checkOutDate
                }
            }
        }

        return ResponseEntity.ok(response)
    }

}
