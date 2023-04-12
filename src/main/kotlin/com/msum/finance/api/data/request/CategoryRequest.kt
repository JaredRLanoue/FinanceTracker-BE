package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.Category
import com.msum.finance.user.data.model.User
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate
import java.math.BigDecimal

data class CategoryRequest(
    val name: String,
    val monthlyBudget: BigDecimal
) {
    init {
        validate(this) {
            validate(CategoryRequest::name)
                .isNotNull()
                .isNotEmpty()
        }
        validate(this) {
            validate(CategoryRequest::monthlyBudget)
                .isNotNull()
        }
    }
}

fun CategoryRequest.toModel(user: User) =
    Category(
        name = name,
        monthlyBudget = monthlyBudget,
        user = user
    )
