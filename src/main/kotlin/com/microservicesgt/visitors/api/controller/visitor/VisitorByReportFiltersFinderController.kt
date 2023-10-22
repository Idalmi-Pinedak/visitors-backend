package com.microservicesgt.visitors.api.controller.visitor

import com.microservicesgt.visitors.api.controller.shared.PageJsonDto
import com.microservicesgt.visitors.api.controller.shared.ReportFiltersJsonDto
import com.microservicesgt.visitors.api.controller.visitor.dto.VisitorJsonDto
import com.microservicesgt.visitors.domain.entities.Country
import com.microservicesgt.visitors.domain.entities.State
import com.microservicesgt.visitors.domain.entities.Visitor
import com.microservicesgt.visitors.domain.repository.CountryRepository
import com.microservicesgt.visitors.domain.repository.GenderRepository
import com.microservicesgt.visitors.domain.repository.StateRepository
import com.microservicesgt.visitors.domain.repository.VisitorRepository
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class VisitorByReportFiltersFinderController(
    private val visitorRepository: VisitorRepository,
    private val genderRepository: GenderRepository,
    private val countryRepository: CountryRepository,
    private val stateRepository: StateRepository
) {

    @PostMapping(value = ["/api/visitors/find"])
    fun find(@RequestBody filters: ReportFiltersJsonDto): ResponseEntity<PageJsonDto<VisitorJsonDto>> {
        val pageRequest = PageRequest.of(filters.pageNumber, filters.pagesize)

        val page = visitorRepository.findByCheckInDateBetween(filters.startDate, filters.endDate, pageRequest)
        val content = transformData(page.content)

        return ResponseEntity.ok(PageJsonDto(content, page.totalElements))
    }

    private fun transformData(visitors: List<Visitor>): List<VisitorJsonDto> {
        val genders = genderRepository.findAll()
        val countries = mutableListOf<Country>()
        val states = mutableListOf<State>()

        return visitors.map { visitor ->

            return@map VisitorJsonDto().apply {
                this.visitorName = visitor.visitorName
                this.age = visitor.age
                this.genderId = visitor.genderId
                this.genderDescription = genders.find { it.id == visitor.genderId }?.description ?: ""
                this.countryId = visitor.countryId
                this.countryName = getCountryName(countries, visitor.countryId ?: 0L)
                this.stateId = visitor.stateId
                this.stateName = getStateName(states, visitor.stateId ?: 0L)
                this.entranceFee = visitor.entranceFee
                this.checkInDate = visitor.checkInDate
                this.checkOutDate = visitor.checkOutDate
                this.visitorGroupId = visitor.visitorGroupId
            }
        }
    }

    private fun getCountryName(countries: MutableList<Country>, countryId: Long): String {
        val inMemoryCountry = countries.find { it.id == countryId }

        if (inMemoryCountry != null) {
            return inMemoryCountry.countryName ?: ""
        }

        val country = countryRepository.findById(countryId)

        if (country.isEmpty) {
            return ""
        }

        countries.add(country.get())

        return country.get().countryName ?: ""
    }

    private fun getStateName(states: MutableList<State>, stateId: Long): String {
        val inMemoryState = states.find { it.id == stateId }

        if (inMemoryState != null) {
            return inMemoryState.stateName ?: ""
        }

        val state = stateRepository.findById(stateId)

        if (state.isEmpty) {
            return ""
        }

        states.add(state.get())

        return state.get().stateName ?: ""
    }

}
