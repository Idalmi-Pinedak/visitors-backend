package com.microservicesgt.visitors.api.controller.shared

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.microservicesgt.visitors.api.converters.CustomLocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonInclude(value = JsonInclude.Include.NON_NULL)
class ReportFiltersJsonDto {
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer::class)
    var startDate: LocalDateTime = LocalDateTime.now()

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer::class)
    var endDate: LocalDateTime = LocalDateTime.now()

    var pageNumber: Int = 0
    var pagesize: Int = 100
}
