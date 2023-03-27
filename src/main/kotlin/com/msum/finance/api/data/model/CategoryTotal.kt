package com.msum.finance.api.data.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

data class CategoryTotal(
    val category: String,
    val total: BigDecimal
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CategoriesTotal(
    val categories: List<CategoryTotal>,
    val total: BigDecimal
)
