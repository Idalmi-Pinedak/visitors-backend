package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.SurveyTemplateDetailValue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyTemplateDetailValueRepository : JpaRepository<SurveyTemplateDetailValue, Long> {

    fun findBySurveyTemplateDetailIdAndActiveTrue(surveyTemplateDetailId: Long): List<SurveyTemplateDetailValue>

}
