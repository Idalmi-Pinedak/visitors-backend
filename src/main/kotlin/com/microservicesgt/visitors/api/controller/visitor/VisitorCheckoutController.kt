package com.microservicesgt.visitors.api.controller.visitor

import com.microservicesgt.visitors.application.visitor.VisitorCheckOutUpdater
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VisitorCheckoutController(
    private val visitorCheckOutUpdater: VisitorCheckOutUpdater
) {

    @PutMapping(value = ["/api/visitor-group/{visitorGroupId}/check-out"])
    fun update(@PathVariable visitorGroupId: Long): ResponseEntity<Unit> {
        visitorCheckOutUpdater.update(visitorGroupId)
        return ResponseEntity.ok(Unit)
    }

}
