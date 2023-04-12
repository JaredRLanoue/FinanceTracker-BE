package com.msum.finance.api.data.model

import com.msum.finance.api.data.entity.ExpenseCategoryEntity
import com.msum.finance.api.data.entity.IncomeCategoryEntity
import com.msum.finance.api.data.view.CategoryView
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Category(
    var id: UUID = UUID.randomUUID(),
    val user: User,
    val name: String,
    val monthlyBudget: BigDecimal = BigDecimal.ZERO,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

fun Category.toView() =
    CategoryView(
        id = id,
        name = name,
        monthlyBudget = monthlyBudget,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun Category.toExpenseCategoryEntity() =
    ExpenseCategoryEntity(
        id = id,
        user = user.toEntity(),
        name = name,
        monthlyBudget = monthlyBudget,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun Category.toIncomeCategoryEntity() =
    // remove the income category table, as it's no longer wanted
    // incomes won't be categorized anymore. maybe just comment out
    IncomeCategoryEntity(
        id = id,
        user = user.toEntity(),
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
