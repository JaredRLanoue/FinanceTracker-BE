package com.msum.finance.api.data.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Transaction(
    var id: UUID = UUID.randomUUID(),
    val account: UUID,
    val type: String,
    val category: String,
    val amount: BigDecimal,
    val counterParty: String,
    val date: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class TransactionList(
    val data: List<Transaction> = listOf(),
    val meta: TransactionMeta
)

data class TransactionMeta(
    val totalExpenses: BigDecimal,
    val totalIncomes: BigDecimal,
    val averageTransaction: BigDecimal
)

fun Expense.toTransaction() = Transaction(
    id = id,
    account = accountId,
    category = category.name,
    amount = amount,
    type = "Expense",
    counterParty = merchantName,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Income.toTransaction() = Transaction(
    id = id,
    account = accountId,
    category = category.name,
    amount = amount,
    type = "Income",
    counterParty = payerName,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt
)
