package com.microservicesgt.visitors.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "survey_template", schema = "visitor")
class SurveyTemplate : HibernateBaseEntity() {

    var description: String? = null

}
