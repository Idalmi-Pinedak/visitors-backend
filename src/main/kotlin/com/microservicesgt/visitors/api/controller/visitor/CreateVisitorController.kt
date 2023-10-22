package com.microservicesgt.visitors.api.controller.visitor

import com.microservicesgt.visitors.api.controller.visitor.dto.VisitorGroupJsonDto
import com.microservicesgt.visitors.application.visitor.VisitorCreator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/visitors"])
class CreateVisitorController(
    private val visitorCreator: VisitorCreator
) {

    @PostMapping
    fun index(@RequestBody visitorGroup: VisitorGroupJsonDto): ResponseEntity<Long> {
        val visitorGroupId = visitorCreator.create(visitorGroup)
        return ResponseEntity.status(HttpStatus.CREATED).body(visitorGroupId)
    }

}
