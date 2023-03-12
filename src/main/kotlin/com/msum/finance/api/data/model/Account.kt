package com.msum.finance.api.data.model

import com.msum.finance.api.data.entity.AccountEntity
import com.msum.finance.api.data.view.AccountView
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import com.msum.finance.user.data.model.toView
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class Account(
    var id: UUID = UUID.randomUUID(),
    val user: User,
    val expenses: List<Expense>,
    val incomes: List<Income>,
    val name: String,
    val type: String,
    val balance: BigDecimal,
    val startingBalance: BigDecimal,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Account.toView() =
    AccountView(
        id = id,
        expenses = expenses.map { it.toView() },
        incomes = incomes.map { it.toView() },
        name = name,
        type = type,
        balance = balance,
        startingBalance = startingBalance,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun Account.toEntity() =
    AccountEntity(
        id = id,
        user = user.toEntity(),
        name = name,
        type = type,
        balance = balance,
        startingBalance = startingBalance,
        expenses = expenses.map { it.toEntity() },
        incomes = incomes.map { it.toEntity() }
    )
