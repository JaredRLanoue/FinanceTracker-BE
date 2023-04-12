package com.msum.finance.api.data.view

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CategoryView(
    val id: UUID,
    val name: String,
    val monthlyBudget: BigDecimal,
    val updatedAt: Instant,
    val createdAt: Instant
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CategoryViewList(
    val categories: List<CategoryView>
)
