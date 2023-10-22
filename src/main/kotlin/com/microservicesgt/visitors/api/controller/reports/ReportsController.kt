package com.microservicesgt.visitors.api.controller.reports

import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.repository.VisitorGroupRepository
import com.microservicesgt.visitors.domain.repository.VisitorRepository
import net.sf.jasperreports.engine.JREmptyDataSource
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.util.JRLoader
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.format.DateTimeFormatter

@RestController
class ReportsController(
    private val visitorGroupRepository: VisitorGroupRepository,
    private val visitorRepository: VisitorRepository
) {

    private companion object {
        const val DATE_TIME_FORMAT = "dd/MM/yyyy"
    }

    private val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)

    @GetMapping(value = ["/api/reports/visitor-group/{visitorGroupId}"])
    fun getVisitorGroupReport(
        @PathVariable(value = "visitorGroupId") visitorGroupId: Long
    ): ResponseEntity<ByteArray> {
        val visitorGroup = visitorGroupRepository
            .findById(visitorGroupId)
            .orElseThrow { ValidationException("Visitor group not exists") }

        val visitors = visitorRepository
            .findByVisitorGroupId(visitorGroup.id ?: 0L)
            .map {
                VisitorJasperReportDto().apply {
                    this.visitorName = it.visitorName ?: ""
                    this.visitorAge = "${it.age?.toInt() ?: 0}"
                    this.price = "${it.entranceFee ?: 0.0}"
                }
            }

        val visitorGroupIdString = visitorGroupId.toString().padStart(5, '0')

        val parameters = mapOf(
            "visitDate" to visitorGroup.checkInDate?.format(dateTimeFormatter),
            "totalVisitors" to "${visitors.size}",
            "totalAmount" to "${visitorGroup.totalAmount}",
            "visitorGroupId" to visitorGroupIdString,
            "visitorDetailDS" to JRBeanCollectionDataSource(visitors)
        )

        // build jasper report
        val reportStream = javaClass.classLoader.getResourceAsStream("visitor_group.jasper")
        val jasperReport = JRLoader.loadObject(reportStream) as JasperReport

        val jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, JREmptyDataSource())
        val pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint)

        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=registro_visitantes_$visitorGroupIdString.pdf")

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes)
    }

}
