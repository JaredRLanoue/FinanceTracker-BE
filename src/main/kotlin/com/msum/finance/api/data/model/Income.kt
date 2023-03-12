package com.msum.finance.api.data.model

import com.msum.finance.api.data.entity.IncomeEntity
import com.msum.finance.api.data.view.IncomeView
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class Income(
    var id: UUID = UUID.randomUUID(),
    val accountId: UUID,
    val user: User,
    val amount: BigDecimal,
    val description: String,
    val category: String,
    val payerName: String,
    val date: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Income.toView() =
    IncomeView(
        id = id,
        amount = amount,
        payerName = payerName,
        description = description,
        category = category,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun Income.toEntity() =
    IncomeEntity(
        id = id,
        amount = amount,
        accountId = accountId,
        user = user.toEntity(),
        payerName = payerName,
        description = description,
        category = category,
        date = date
    )
