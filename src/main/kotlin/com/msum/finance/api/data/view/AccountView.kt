package com.msum.finance.api.data.view

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class AccountView(
    val id: UUID,
    val expenses: List<ExpenseView>,
    val incomes: List<IncomeView>,
    val name: String,
    val type: String,
    val balance: BigDecimal,
    val createdAt: Instant,
    val updatedAt: Instant
)
