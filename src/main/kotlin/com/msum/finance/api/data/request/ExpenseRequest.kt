package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.Expense
import com.msum.finance.api.data.model.ExpenseCategory
import com.msum.finance.user.data.model.User
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class ExpenseRequest(
    val accountId: UUID,
    val categoryId: UUID,
    val amount: BigDecimal,
    val description: String,
    val merchantName: String,
    val pending: Boolean,
    val date: Instant = Instant.now(),
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

fun ExpenseRequest.toModel(user: User, expenseCategory: ExpenseCategory) =
    Expense(
        user = user,
        expenseCategory = expenseCategory,
        accountId = accountId,
        amount = if (amount < BigDecimal.ZERO) amount else amount.negate(),
        description = description,
        merchantName = merchantName,
        pending = pending,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
