package com.microservicesgt.visitors.api.controller.visitor.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.microservicesgt.visitors.api.converters.CustomLocalDateTimeSerializer
import java.time.LocalDateTime

@JsonInclude(value = JsonInclude.Include.NON_NULL)
class VisitorJsonDto {
    var visitorName: String? = null
    var age: Short? = null
    var genderId: Long? = null
    var genderDescription: String? = null
    var countryId: Long? = null
    var countryName: String? = null
    var stateId: Long? = null
    var stateName: String? = null
    var entranceFee: Double? = null

    @JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    var checkInDate: LocalDateTime? = null

    @JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    var checkOutDate: LocalDateTime? = null

    var visitorGroupId: Long? = null
}
