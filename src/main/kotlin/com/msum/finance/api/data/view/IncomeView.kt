package com.msum.finance.api.data.view

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class IncomeView(
    val id: UUID,
    val amount: BigDecimal,
    val name: String,
    val date: Instant,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)
