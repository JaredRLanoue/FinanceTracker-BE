package com.msum.finance.api.data.model

import com.msum.finance.api.data.view.ExpenseView
import com.msum.finance.user.data.model.User
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Expense(
    val id: UUID,
    val accountId: UUID,
    val user: User,
    val category: String,
    val location: Location,
    val amount: BigDecimal,
    val description: String,
    val merchantName: String,
    val pending: Boolean,
    val date: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Expense.toView() =
    ExpenseView(
        id = id,
        category = category,
        location = location.toView(),
        amount = amount,
        description = description,
        merchantName = merchantName,
        pending = pending,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt

    )
