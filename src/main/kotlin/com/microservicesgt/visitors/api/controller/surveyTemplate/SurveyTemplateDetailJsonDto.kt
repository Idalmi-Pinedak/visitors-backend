package com.microservicesgt.visitors.api.controller.surveyTemplate

class SurveyTemplateDetailJsonDto {
    var id: Long? = null
    var fieldDescriptionEs: String? = null
    var fieldDescriptionEn: String? = null

    var values = listOf<SurveyTemplateDetailValueJsonDto>()
}
