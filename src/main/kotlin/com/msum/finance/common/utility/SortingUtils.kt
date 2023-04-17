package com.msum.finance.common.utility

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters

object SortingUtils {
    fun getSortMethodRange(sortMethod: String): Pair<Instant, Instant> {
        val startDate: Instant
        val endDate: Instant

        when (sortMethod) {
            "week" -> {
                startDate = LocalDate.now().minusDays(6).atStartOfDay().toInstant(ZoneOffset.UTC)
                endDate = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC)
            }

            "month" -> {
                startDate = LocalDate.now().withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC)
                endDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay()
                    .toInstant(ZoneOffset.UTC)
            }

            "year" -> {
                startDate =
                    LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay().toInstant(ZoneOffset.UTC)
                endDate =
                    LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atStartOfDay().toInstant(ZoneOffset.UTC)
            }

            "all" -> {
                startDate = LocalDate.MIN.atStartOfDay().toInstant(ZoneOffset.UTC)
                endDate = LocalDate.of(9999, 12, 31).atStartOfDay().toInstant(ZoneOffset.UTC)
            }

            else -> {
                startDate = LocalDate.MIN.atStartOfDay().toInstant(ZoneOffset.UTC)
                endDate = LocalDate.of(9999, 12, 31).atStartOfDay().toInstant(ZoneOffset.UTC)
            }
        }
        return Pair(startDate, endDate)
    }
}
