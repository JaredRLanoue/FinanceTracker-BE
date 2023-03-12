package com.msum.finance.api.data.model

import com.msum.finance.api.data.entity.ExpenseCategoryEntity
import com.msum.finance.api.data.view.ExpenseCategoryView
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import java.time.Instant
import java.util.UUID

data class ExpenseCategory(
    val id: UUID = UUID.randomUUID(),
    val user: User,
    val name: String,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

fun ExpenseCategory.toView() =
    ExpenseCategoryView(
        id = id,
        name = name
    )

fun ExpenseCategory.toEntity() =
    ExpenseCategoryEntity(
        id = id,
        user = user.toEntity(),
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
