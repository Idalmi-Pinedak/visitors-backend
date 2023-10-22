package com.microservicesgt.visitors.api.controller.surveyTemplate

import com.microservicesgt.visitors.domain.repository.SurveyTemplateDetailRepository
import com.microservicesgt.visitors.domain.repository.SurveyTemplateDetailValueRepository
import com.microservicesgt.visitors.domain.repository.SurveyTemplateRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/survey-template"])
class SurveyTemplateController(
    private val surveyTemplateRepository: SurveyTemplateRepository,
    private val surveyTemplateDetailRepository: SurveyTemplateDetailRepository,
    private val surveyTemplateDetailValueRepository: SurveyTemplateDetailValueRepository
) {

    @GetMapping
    fun find(): ResponseEntity<List<SurveyTemplateDetailJsonDto>> {
        val templateList = surveyTemplateRepository.findAll()

        if (templateList.isEmpty()) return ResponseEntity.ok(listOf())

        // 1. get template
        val templateId = templateList[0]?.id ?: 0L

        // 2. get all survey questions
        val questions = surveyTemplateDetailRepository
            .findBySurveyTemplateIdAndActiveTrue(templateId)
            .map {
                SurveyTemplateDetailJsonDto().apply {
                    this.id = it.id
                    this.fieldDescriptionEs = it.fieldDescriptionEs
                    this.fieldDescriptionEn = it.fieldDescriptionEn
                    this.values = getDetailValues(it.id ?: 0L)
                }
            }

        return ResponseEntity.ok(questions)
    }

    private fun getDetailValues(surveyTemplateDetailId: Long): List<SurveyTemplateDetailValueJsonDto> {
        return surveyTemplateDetailValueRepository
            .findBySurveyTemplateDetailIdAndActiveTrue(surveyTemplateDetailId)
            .map {
                SurveyTemplateDetailValueJsonDto().apply {
                    this.id = it.id
                    this.descriptionEs = it.descriptionEs
                    this.descriptionEn = it.descriptionEn
                }
            }
    }

}
