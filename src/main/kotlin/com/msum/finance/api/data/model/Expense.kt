package com.msum.finance.api.data.model

import com.msum.finance.api.data.entity.ExpenseEntity
import com.msum.finance.api.data.view.ExpenseView
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Expense(
    var id: UUID = UUID.randomUUID(),
    val accountId: UUID,
    val user: User,
    val category: String,
//    val locationId: UUID,
    val amount: BigDecimal,
    val description: String,
    val merchantName: String,
    val pending: Boolean,
    val date: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Expense.toView() = ExpenseView(
    id = id,
    category = category,
//    locationId = locationId,
    amount = amount,
    description = description,
    merchantName = merchantName,
    pending = pending,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt

)

fun Expense.toEntity() = ExpenseEntity(
    id = id,
    accountId = accountId,
    user = user.toEntity(),
    category = category,
//    locationId = locationId,
    amount = amount,
    description = description,
    merchantName = merchantName,
    pending = pending,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt
)
