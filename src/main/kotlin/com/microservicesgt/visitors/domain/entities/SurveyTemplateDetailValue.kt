package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "survey_template_detail_value", schema = "visitor")
class SurveyTemplateDetailValue : HibernateBaseEntity() {

    @Column(name = "description_es")
    var descriptionEs: String? = null

    @Column(name = "description_en")
    var descriptionEn: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var surveyTemplateDetail: SurveyTemplateDetail? = null

}
