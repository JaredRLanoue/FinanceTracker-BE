package com.msum.finance.api.data.view

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ExpenseView(
    val id: UUID,
    val category: String,
//    val locationId: UUID,
    val amount: BigDecimal,
    val description: String,
    val merchantName: String?,
    val pending: Boolean,
    val date: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)
