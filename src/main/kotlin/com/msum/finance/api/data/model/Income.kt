package com.msum.finance.api.data.model

import com.msum.finance.api.data.view.IncomeView
import com.msum.finance.user.data.model.User
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class Income(
    val id: UUID,
    val accountId: UUID,
    val user: User,
    val amount: BigDecimal,
    val name: String,
    val date: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Income.toView() =
    IncomeView(
        id = id,
        amount = amount,
        name = name,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
