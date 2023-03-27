package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.Category
import com.msum.finance.api.data.model.Expense
import com.msum.finance.user.data.model.User
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class ExpenseRequest(
    val accountId: UUID,
    val categoryId: UUID,
    val amount: BigDecimal,
    val merchantName: String,
    val description: String,
    val pending: Boolean = false,
    val date: Instant = Instant.now(),
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
) {
    init {
        validate(this) {
            validate(ExpenseRequest::merchantName)
                .isNotNull()
                .isNotEmpty()
            validate(ExpenseRequest::amount)
                .isNotNull()
        }
    }
}

fun ExpenseRequest.toModel(user: User, category: Category) =
    Expense(
        user = user,
        category = category,
        accountId = accountId,
        amount = if (amount < BigDecimal.ZERO) amount else amount.negate(),
        description = description,
        merchantName = merchantName,
        pending = pending,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
