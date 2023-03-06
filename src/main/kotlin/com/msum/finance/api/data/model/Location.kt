package com.msum.finance.api.data.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.msum.finance.api.data.view.LocationView
import java.time.Instant
import java.util.UUID

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Location(
    val id: UUID = UUID.randomUUID(),
    val address: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Location.toView() =
    LocationView(
        id = id,
        address = address,
        city = city,
        state = state,
        country = country,
        postalCode = postalCode,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
