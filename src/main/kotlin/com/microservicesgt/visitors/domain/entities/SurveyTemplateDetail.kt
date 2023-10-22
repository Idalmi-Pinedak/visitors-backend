package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "survey_template_detail", schema = "visitor")
class SurveyTemplateDetail : HibernateBaseEntity() {

    @Column(name = "field_description_es")
    var fieldDescriptionEs: String? = null

    @Column(name = "field_description_en")
    var fieldDescriptionEn: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "field_type")
    var fieldType: SurveyFieldType? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var surveyTemplate: SurveyTemplate? = null

}
