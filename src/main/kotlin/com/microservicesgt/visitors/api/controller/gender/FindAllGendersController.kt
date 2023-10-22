package com.microservicesgt.visitors.api.controller.gender

import com.microservicesgt.visitors.domain.repository.GenderRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/gender/all"])
class FindAllGendersController(
    private val genderRepository: GenderRepository
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<GenderJsonDto>> {
        val result = genderRepository
            .findAllByActiveTrue()
            .map {
                GenderJsonDto().apply {
                    this.id = it.id
                    this.description = it.description
                }
            }

        return ResponseEntity.ok(result)
    }

}
