package com.microservicesgt.visitors.domain.shared

import java.time.LocalDateTime

object DateUtils {

    fun getCurrentDateTime(hour: Int = 0, minute: Int = 0, second: Int = 0): LocalDateTime {
        return LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(second)
    }

}
