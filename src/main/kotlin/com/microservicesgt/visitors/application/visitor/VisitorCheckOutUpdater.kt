package com.microservicesgt.visitors.application.visitor

import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.repository.VisitorGroupRepository
import com.microservicesgt.visitors.domain.repository.VisitorRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class VisitorCheckOutUpdater(
    private val visitorGroupRepository: VisitorGroupRepository,
    private val visitorRepository: VisitorRepository
) {

    fun update(visitorGroupId: Long) {
        val visitorGroup = visitorGroupRepository
            .findById(visitorGroupId)
            .orElseThrow { ValidationException("Visitor group not exists") }

        visitorGroup.checkOutDate = LocalDateTime.now()

        val visitors = visitorRepository
            .findByVisitorGroupId(visitorGroup.id ?: 0L)

        visitors.forEach {
            it.checkOutDate = LocalDateTime.now()
        }

        visitorGroupRepository.save(visitorGroup)
        visitorRepository.saveAll(visitors)
    }

}
