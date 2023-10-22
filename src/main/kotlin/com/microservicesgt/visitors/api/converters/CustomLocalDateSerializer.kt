package com.microservicesgt.visitors.api.converters

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CustomLocalDateSerializer(t: Class<LocalDate>? = null) : StdSerializer<LocalDate>(t) {
    override fun serialize(value: LocalDate, gen: JsonGenerator, arg2: SerializerProvider) {
        val format = "yyyy-MM-dd"
        val formatter = DateTimeFormatter.ofPattern(format)
        gen.writeString(formatter.format(value))
    }
}
