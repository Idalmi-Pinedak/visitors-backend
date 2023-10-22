package com.microservicesgt.visitors.api.converters

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomLocalDateTimeSerializer(t: Class<LocalDateTime>? = null) : StdSerializer<LocalDateTime>(t) {
    override fun serialize(value: LocalDateTime, gen: JsonGenerator, arg2: SerializerProvider) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        gen.writeString(formatter.format(value))
    }
}
