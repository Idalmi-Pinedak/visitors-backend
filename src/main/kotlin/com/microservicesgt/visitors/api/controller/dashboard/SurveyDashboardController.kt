package com.microservicesgt.visitors.api.controller.dashboard

import com.microservicesgt.visitors.api.controller.dashboard.dto.SurveyDashboardJsdonDto
import com.microservicesgt.visitors.domain.repository.VisitorSurveyRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SurveyDashboardController(
    private val visitorSurveyRepository: VisitorSurveyRepository
) {

    @GetMapping(value = ["/api/survey-dashboard/{questionId}"])
    fun getDataByQuestion(@PathVariable(value = "questionId") questionId: Long) : ResponseEntity<List<SurveyDashboardJsdonDto>> {
        val result = visitorSurveyRepository
            .countBySurveyTemplateDetailId(questionId)
            .map {
                SurveyDashboardJsdonDto().apply {
                    this.month = it[0].toString().toInt()
                    this.responseId = it[1].toString().toLong()
                    this.count = it[2].toString().toInt()
                }
            }

        return ResponseEntity.ok(result)
    }

}
