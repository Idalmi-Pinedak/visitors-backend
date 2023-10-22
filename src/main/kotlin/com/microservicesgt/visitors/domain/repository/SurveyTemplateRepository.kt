package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.SurveyTemplate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyTemplateRepository : JpaRepository<SurveyTemplate, Long> {
}
