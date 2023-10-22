package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.VisitorSurvey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VisitorSurveyRepository : JpaRepository<VisitorSurvey, Long> {

    @Query(
        value = """
            select cast(extract('month' from t0.created_at) as int) as month,
                   t1.response as response_id,
                   count(1) total_responses
            from visitor.visitor_survey as t0
                     cross join lateral jsonb_array_elements(t0.responses) as t1(response)
                     inner join visitor.survey_template_detail_value as t2 on t2.id = cast(t1.response as bigint)
            where t0.survey_template_detail_id = :surveyTemplateDetailId
            group by extract('month' from t0.created_at), t1.response;
        """,
        nativeQuery = true
    )
    fun countBySurveyTemplateDetailId(
        @Param(value = "surveyTemplateDetailId") surveyTemplateDetailId: Long
    ): List<Array<*>>

}
