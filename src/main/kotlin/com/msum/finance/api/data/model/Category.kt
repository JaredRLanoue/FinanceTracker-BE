package com.msum.finance.api.data.model

import com.msum.finance.api.data.entity.ExpenseCategoryEntity
import com.msum.finance.api.data.entity.IncomeCategoryEntity
import com.msum.finance.api.data.view.CategoryView
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import java.time.Instant
import java.util.UUID

data class Category(
    val id: UUID = UUID.randomUUID(),
    val user: User,
    val name: String,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

fun Category.toView() =
    CategoryView(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun Category.toExpenseCategoryEntity() =
    ExpenseCategoryEntity(
        id = id,
        user = user.toEntity(),
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun Category.toIncomeCategoryEntity() =
    IncomeCategoryEntity(
        id = id,
        user = user.toEntity(),
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
