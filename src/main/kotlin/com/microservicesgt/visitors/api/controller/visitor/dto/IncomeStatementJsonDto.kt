package com.microservicesgt.visitors.api.controller.visitor.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.time.LocalDate

@JsonInclude(value = JsonInclude.Include.NON_NULL)
class IncomeStatementJsonDto {

    @JsonSerialize(using = LocalDateSerializer::class)
    var date: LocalDate? = null

    var amount: Double? = null
}
