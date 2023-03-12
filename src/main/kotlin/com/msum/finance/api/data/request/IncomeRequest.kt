package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.Income
import com.msum.finance.user.data.model.User
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class IncomeRequest(
    val accountId: UUID,
    val payerName: String,
    val description: String,
    val date: Instant = Instant.now(),
    val amount: BigDecimal,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

fun IncomeRequest.toModel(user: User) =
    Income(
        user = user,
        accountId = accountId,
        payerName = payerName,
        description = description,
        date = date,
        amount = amount,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
