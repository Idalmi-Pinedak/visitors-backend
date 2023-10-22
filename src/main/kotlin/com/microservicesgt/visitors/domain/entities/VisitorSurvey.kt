package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "visitor_survey", schema = "visitor")
class VisitorSurvey : HibernateBaseEntity() {

    @Column(name = "visitor_id")
    var visitorId: Long? = null

    @Column(name = "survey_template_detail_id")
    var surveyTemplateDetailId: Long? = null

    @JdbcTypeCode(SqlTypes.JSON)
    var responses: List<Int> = listOf()

    @Column(name = "other_response")
    var otherResponse: String? = null

}
