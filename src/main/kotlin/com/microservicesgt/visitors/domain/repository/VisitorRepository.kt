package com.microservicesgt.visitors.domain.repository

import com.microservicesgt.visitors.domain.entities.Visitor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface VisitorRepository : JpaRepository<Visitor, Long> {

    fun findByVisitorGroupId(visitorGroupId: Long): List<Visitor>

    @Query(
        value = """
            select count(distinct t0.id)
            from Visitor as t0
            where t0.createdAt between :startDate and :endDate
        """
    )
    fun countByCreatedAtBetween(
        @Param(value = "startDate") startDate: LocalDateTime,
        @Param(value = "endDate") endDate: LocalDateTime
    ): Long

    @Query(
        value = """
            select coalesce(sum(t0.entranceFee), 0) 
            from Visitor as t0
            where t0.createdAt between :startDate and :endDate
        """
    )
    fun sumEntranceFeeByCreatedAtBetween(
        @Param(value = "startDate") startDate: LocalDateTime,
        @Param(value = "endDate") endDate: LocalDateTime
    ): Double

    @Query(
        value = """
            select months.month, coalesce(counts.count, 0)
            from (
                    select extract('month' from t0.created_at) as month, count(t0.id) as count
                    from visitor.visitor as t0
                    where extract('year' from t0.created_at) = :year
                    group by extract('month' from t0.created_at)
            ) as counts
            right join generate_series(1, 12) as months(month) on months.month = counts.month
            where months.month <= :month
            order by months.month;
        """,
        nativeQuery = true
    )
    fun countGroupByMonth(@Param(value = "year") year: Int, @Param(value = "month") month: Int): List<Array<*>>

    @Query(
        value = """
            select months.month, coalesce(sums.amount, 0)
            from (
                    select extract('month' from t0.created_at) as month, coalesce(sum(t0.entrance_fee), 0) as amount
                    from visitor.visitor as t0
                    where extract('year' from t0.created_at) = :year
                    group by extract('month' from t0.created_at)
            ) as sums
            right join generate_series(1, 12) as months(month) on months.month = sums.month
            where months.month <= :month
            order by months.month;
        """,
        nativeQuery = true
    )
    fun revenueGroupByMonth(@Param(value = "year") year: Int, @Param(value = "month") month: Int): List<Array<*>>

    @Query(
        value = """
            select t1.state_name, count(t0.id)
            from visitor.visitor as t0
                     join visitor.state as t1 on t1.id = t0.state_id
            where extract('year' from t0.created_at) = :year
              and t0.country_id = :countryId
            group by t1.state_name
            order by count(t0.id) desc;
        """,
        nativeQuery = true
    )
    fun countByCountryIdGroupByStateName(
        @Param(value = "year") year: Int,
        @Param(value = "countryId") countryId: Long
    ): List<Array<*>>

    @Query(
        value = """
            from Visitor as t0
            where t0.checkInDate between :startDate and :endDate 
            order by t0.checkInDate desc
            """
    )
    fun findByCheckInDateBetween(
        @Param(value = "startDate") startDate: LocalDateTime,
        @Param(value = "endDate") endDate: LocalDateTime,
        pageable: Pageable
    ): Page<Visitor>

    @Query(
        value = """
            select date(created_at), coalesce(sum(entrance_fee), 0)
            from visitor.visitor
            where visitor.created_at between :startDate and :endDate
            group by date(created_at)
            order by date(created_at) desc
        """,
        nativeQuery = true
    )
    fun findIncomeStatementByDateBetween(
        @Param(value = "startDate") startDate: LocalDateTime,
        @Param(value = "endDate") endDate: LocalDateTime
    ): List<Array<*>>

}
