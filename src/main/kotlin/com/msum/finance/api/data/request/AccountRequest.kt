package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.Account
import com.msum.finance.user.data.model.User
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class AccountRequest(
    val name: String,
    val type: String,
    val startingBalance: BigDecimal,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

fun AccountRequest.toModel(user: User) =
    Account(
        user = user,
        name = name,
        type = type,
        balance = startingBalance,
        startingBalance = startingBalance,
        createdAt = createdAt,
        updatedAt = updatedAt,
        expenses = emptyList(),
        incomes = emptyList()
    )
