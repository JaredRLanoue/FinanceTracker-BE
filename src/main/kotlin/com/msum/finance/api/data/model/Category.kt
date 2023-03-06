package com.msum.finance.api.data.model

import com.msum.finance.api.data.view.CategoryView
import java.time.Instant
import java.util.UUID

data class Category(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Category.toView() =
    CategoryView(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
