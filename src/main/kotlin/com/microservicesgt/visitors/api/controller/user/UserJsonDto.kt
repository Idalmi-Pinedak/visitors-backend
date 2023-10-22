package com.microservicesgt.visitors.api.controller.user

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.microservicesgt.visitors.api.converters.CustomLocalDateTimeDeserializer
import com.microservicesgt.visitors.api.converters.CustomLocalDateTimeSerializer
import java.time.LocalDateTime

class UserJsonDto {
    var id: Long? = 0L
    var name: String? = ""
    var email: String? = ""
    var password: String? = ""
    var active: Boolean? = true
    var roleId: Long? = 0L

    @JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer::class)
    var createdAt: LocalDateTime? = LocalDateTime.now()

    var createdBy: String? = ""

    @JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer::class)
    var modifiedAt: LocalDateTime? = LocalDateTime.now()

    var modifiedBy: String? = ""

    companion object {
        fun build(init: UserJsonDto.() -> Unit) = UserJsonDto().apply(init)
    }
}
