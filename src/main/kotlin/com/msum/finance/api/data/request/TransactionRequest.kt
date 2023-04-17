package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.Account
import com.msum.finance.api.data.model.Category
import com.msum.finance.api.data.model.Expense
import com.msum.finance.api.data.model.Income
import com.msum.finance.user.data.model.User
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class TransactionRequest(
    val accountId: String,
    val categoryId: String,
    val type: String,
    val counterParty: String,
    val amount: BigDecimal,
    val date: String,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
) {
    init {
        validate(this) {
            validate(TransactionRequest::counterParty)
                .isNotNull()
                .isNotEmpty()
            validate(TransactionRequest::amount)
                .isNotNull()
            validate(TransactionRequest::accountId)
                .isNotNull()
            validate(TransactionRequest::categoryId)
                .isNotNull()
            validate(TransactionRequest::type)
                .isNotNull()
                .isNotEmpty()
            validate(TransactionRequest::amount)
                .isNotNull()
            validate(TransactionRequest::date)
                .isNotNull()
        }
    }
}

fun TransactionRequest.toExpenseModel(user: User, category: Category, account: Account) =
    Expense(
        user = user,
        category = category,
        accountId = account.id, // TODO: Maybe change to just accountId from above
        amount = amount.negate().abs(),
        description = "",
        pending = false,
        merchantName = counterParty,
        date = Instant.parse(date),
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun TransactionRequest.toIncomeModel(user: User, category: Category, account: Account) =
    Income(
        user = user,
        category = category,
        accountId = account.id,
        amount = amount,
        description = "",
        payerName = counterParty,
        date = Instant.parse(date),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
