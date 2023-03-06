package com.msum.finance.api.data.view

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.Instant
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LocationView(
    val id: UUID,
    val address: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
