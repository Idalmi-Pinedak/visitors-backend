package com.microservicesgt.visitors.application.visitor

import com.microservicesgt.visitors.api.controller.visitor.dto.VisitorGroupJsonDto
import com.microservicesgt.visitors.domain.entities.Visitor
import com.microservicesgt.visitors.domain.entities.VisitorGroup
import com.microservicesgt.visitors.domain.entities.VisitorSurvey
import com.microservicesgt.visitors.domain.repository.VisitorGroupRepository
import com.microservicesgt.visitors.domain.repository.VisitorRepository
import com.microservicesgt.visitors.domain.repository.VisitorSurveyRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class VisitorCreator(
    private val visitorRepository: VisitorRepository,
    private val visitorSurveyRepository: VisitorSurveyRepository,
    private val visitorGroupRepository: VisitorGroupRepository
) {

    fun create(visitorGroup: VisitorGroupJsonDto): Long {
        val visitors = visitorGroup.visitors ?: listOf()
        val survey = visitorGroup.survey ?: listOf()

        val total = visitors.map { it.entranceFee ?: 0.0 }.reduce { acc, d -> acc + d }

        val visitorGroupEntity = VisitorGroup().apply {
            this.checkInDate = LocalDateTime.now()
            this.totalVisitors = visitors.size
            this.totalAmount = total
        }

        visitorGroupRepository.save(visitorGroupEntity)

        val visitorEntities = visitors.map {
            return@map Visitor().apply {
                this.visitorName = it.visitorName
                this.age = it.age
                this.entranceFee = it.entranceFee
                this.genderId = it.genderId
                this.countryId = it.countryId
                this.stateId = it.stateId
                this.visitorGroupId = visitorGroupEntity.id
                this.checkInDate = LocalDateTime.now()
            }
        }

        visitorRepository.saveAll(visitorEntities)

        visitorEntities.forEach { visitor ->
            val surveyResponses = survey.map {
                return@map VisitorSurvey().apply {
                    this.visitorId = visitor.id
                    this.surveyTemplateDetailId = it.surveyTemplateDetailId
                    this.responses = it.responses
                }
            }

            visitorSurveyRepository.saveAll(surveyResponses)
        }

        return visitorGroupEntity.id ?: 0L
    }

}
