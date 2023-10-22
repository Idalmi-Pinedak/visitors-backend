package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.SurveyTemplateDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyTemplateDetailRepository : JpaRepository<SurveyTemplateDetail, Long> {

    fun findBySurveyTemplateIdAndActiveTrue(surveyTemplateId: Long): List<SurveyTemplateDetail>

}
