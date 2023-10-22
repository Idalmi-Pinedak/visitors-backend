package com.microservicesgt.visitors.api.converters

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.time.LocalDate

class CustomLocalDateDeserializer(t: Class<LocalDate>? = null) : StdDeserializer<LocalDate>(t) {
    override fun deserialize(jsonParser: JsonParser?, p1: DeserializationContext?): LocalDate {
        return LocalDate.parse(jsonParser?.readValueAs(String::class.java))
    }
}
