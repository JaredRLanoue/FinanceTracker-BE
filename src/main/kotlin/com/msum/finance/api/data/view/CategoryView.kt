package com.msum.finance.api.data.view

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.Instant
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CategoryView(
    val id: UUID,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
