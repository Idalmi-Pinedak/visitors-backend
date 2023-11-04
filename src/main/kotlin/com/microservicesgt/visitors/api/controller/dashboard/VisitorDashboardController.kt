package com.microservicesgt.visitors.api.controller.dashboard

import com.microservicesgt.visitors.api.controller.dashboard.dto.DashboardResponseJsonDto
import com.microservicesgt.visitors.domain.repository.VisitorRepository
import com.microservicesgt.visitors.domain.shared.DateUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@RestController
class VisitorDashboardController(
    private val visitorRepository: VisitorRepository
) {

    @GetMapping(value = ["/api/dashboard/visitors-today"])
    fun visitorsToday(): ResponseEntity<Int> {
        val startDate = DateUtils.getCurrentDateTime()
        val endDate = DateUtils.getCurrentDateTime(23, 59, 59)

        val result = visitorRepository.countByCreatedAtBetween(startDate, endDate)

        return ResponseEntity.ok(result.toInt())
    }

    @GetMapping(value = ["/api/dashboard/visitors-in-the-month"])
    fun visitorsInTheMonth(): ResponseEntity<Int> {
        val startDate = DateUtils.getCurrentDateTime().with(TemporalAdjusters.firstDayOfMonth())
        val endDate = DateUtils.getCurrentDateTime(23, 59, 59).with(TemporalAdjusters.lastDayOfMonth())

        val result = visitorRepository.countByCreatedAtBetween(startDate, endDate)

        return ResponseEntity.ok(result.toInt())
    }

    @GetMapping(value = ["/api/dashboard/visitors-in-the-last-month"])
    fun visitorsInTheLastMonth(): ResponseEntity<Int> {
        val startDate = DateUtils.getCurrentDateTime()
            .minusMonths(1) // le restamos un mes para obtener la fecha inicio del mes anterior
            .with(TemporalAdjusters.firstDayOfMonth())

        val endDate = DateUtils.getCurrentDateTime(23, 59, 59)
            .minusMonths(1) // le restamos un mes para obtener la fecha fin del mes anterior
            .with(TemporalAdjusters.lastDayOfMonth())

        val result = visitorRepository.countByCreatedAtBetween(startDate, endDate)

        return ResponseEntity.ok(result.toInt())
    }

    @GetMapping(value = ["/api/dashboard/visitors-by-year"])
    fun visitorsByYear(
        @RequestParam(value = "yearToSearch", defaultValue = "current") yearToSearch: String
    ): ResponseEntity<List<DashboardResponseJsonDto>> {
        val currentDate = LocalDate.now()

        val year = when {
            yearToSearch == "current" -> currentDate.year
            else -> currentDate.minusYears(1).year
        }

        val month = when {
            yearToSearch == "current" -> currentDate.monthValue
            else -> 12
        }

        val data = visitorRepository
            .countGroupByMonth(year, month)
            .map {
                return@map DashboardResponseJsonDto().apply {
                    this.month = it[0].toString().toInt()
                    this.count = it[1].toString().toInt()
                }
            }

        return ResponseEntity.ok(data)
    }

    @GetMapping(value = ["/api/dashboard/visitors-by-state"])
    fun visitorsByState(
        @RequestParam(value = "yearToSearch", defaultValue = "current") yearToSearch: String,
        @RequestParam(value = "countryId", defaultValue = "1") countryId: Long
    ): ResponseEntity<List<DashboardResponseJsonDto>> {
        val currentDate = LocalDate.now()

        val year = when {
            yearToSearch == "current" -> currentDate.year
            else -> currentDate.minusYears(1).year
        }

        val data = visitorRepository
            .countByCountryIdGroupByStateName(year, countryId)
            .map {
                return@map DashboardResponseJsonDto().apply {
                    this.stateName = it[0].toString()
                    this.count = it[1].toString().toInt()
                }
            }

        return ResponseEntity.ok(data)
    }

    @GetMapping(value = ["/api/dashboard/visitors-by-country"])
    fun visitorsByCountry(
        @RequestParam(value = "yearToSearch", defaultValue = "current") yearToSearch: String,
    ): ResponseEntity<List<DashboardResponseJsonDto>> {
        val currentDate = LocalDate.now()

        val year = when {
            yearToSearch == "current" -> currentDate.year
            else -> currentDate.minusYears(1).year
        }

        val data = visitorRepository
            .countByCountry(year)
            .map {
                return@map DashboardResponseJsonDto().apply {
                    this.countryName = it[0].toString()
                    this.count = it[1].toString().toInt()
                }
            }

        return ResponseEntity.ok(data)
    }

}
