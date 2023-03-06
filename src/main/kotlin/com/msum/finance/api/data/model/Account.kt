package com.msum.finance.api.data.model

import com.msum.finance.api.data.view.AccountView
import com.msum.finance.user.data.model.User
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class Account(
    val id: UUID = UUID.randomUUID(),
    val user: User,
    val expenses: List<Expense>,
    val incomes: List<Income>,
    val name: String,
    val type: String,
    val balance: BigDecimal,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Account.toView() = AccountView(
    id = id,
    expenses = expenses.map { it.toView() },
    incomes = incomes.map { it.toView() },
    name = name,
    type = type,
    balance = balance,
    createdAt = createdAt,
    updatedAt = updatedAt
)
