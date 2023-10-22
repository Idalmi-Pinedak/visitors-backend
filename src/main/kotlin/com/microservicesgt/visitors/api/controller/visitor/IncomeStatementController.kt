package com.microservicesgt.visitors.api.controller.visitor

import com.microservicesgt.visitors.api.controller.shared.ReportFiltersJsonDto
import com.microservicesgt.visitors.api.controller.visitor.dto.IncomeStatementJsonDto
import com.microservicesgt.visitors.domain.repository.VisitorRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.sql.Date as SqlDate

@RestController
class IncomeStatementController(
    private val visitorRepository: VisitorRepository
) {

    @PostMapping(value = ["/api/visitors/income-statement"])
    fun find(@RequestBody reportFilters: ReportFiltersJsonDto): ResponseEntity<List<IncomeStatementJsonDto>> {
        val result = visitorRepository
            .findIncomeStatementByDateBetween(reportFilters.startDate, reportFilters.endDate)
            .map {
                return@map IncomeStatementJsonDto().apply {
                    this.date = (it[0] as SqlDate).toLocalDate()
                    this.amount = it[1].toString().toDouble()
                }
            }

        return ResponseEntity.ok(result)
    }

}
