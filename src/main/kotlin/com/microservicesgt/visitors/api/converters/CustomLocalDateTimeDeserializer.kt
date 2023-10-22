package com.microservicesgt.visitors.api.converters

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.time.LocalDateTime

class CustomLocalDateTimeDeserializer(t: Class<LocalDateTime>? = null) : StdDeserializer<LocalDateTime>(t) {
    override fun deserialize(jsonParser: JsonParser?, p1: DeserializationContext?): LocalDateTime {
        return LocalDateTime.parse(jsonParser?.readValueAs(String::class.java))
    }
}
