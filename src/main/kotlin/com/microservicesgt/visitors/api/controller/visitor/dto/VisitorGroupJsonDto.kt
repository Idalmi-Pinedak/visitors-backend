package com.microservicesgt.visitors.api.controller.visitor.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.microservicesgt.visitors.api.converters.CustomLocalDateTimeSerializer
import java.time.LocalDateTime

@JsonInclude(value = JsonInclude.Include.NON_NULL)
class VisitorGroupJsonDto {
    var id: Long? = null

    @JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    var checkInDate: LocalDateTime? = null

    @JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    var checkOutDate: LocalDateTime? = null
    var totalVisitors: Int? = null
    var totalAmount: Double? = null
    var visitors: List<VisitorJsonDto>? = listOf()
    var survey: List<VisitorSurveyJsonDto>? = listOf()
}
