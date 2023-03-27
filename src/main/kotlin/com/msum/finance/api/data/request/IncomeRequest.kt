package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.Category
import com.msum.finance.api.data.model.Income
import com.msum.finance.user.data.model.User
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class IncomeRequest(
    val accountId: UUID,
    val categoryId: UUID,
    val amount: BigDecimal,
    val payerName: String,
    val description: String,
    val date: Instant = Instant.now(),
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
) {
    init {
        validate(this) {
            validate(IncomeRequest::payerName)
                .isNotNull()
                .isNotEmpty()
            validate(IncomeRequest::amount)
                .isNotNull() // add position validation here instead of below
        }
    }
}

fun IncomeRequest.toModel(user: User, category: Category) =
    Income(
        user = user,
        category = category,
        accountId = accountId,
        amount = if (amount > BigDecimal.ZERO) amount else amount.negate(),
        payerName = payerName,
        description = description,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
