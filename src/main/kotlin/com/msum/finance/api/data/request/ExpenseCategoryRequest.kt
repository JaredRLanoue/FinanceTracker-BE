package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.ExpenseCategory
import com.msum.finance.user.data.model.User

data class ExpenseCategoryRequest(
    val name: String
)

fun ExpenseCategoryRequest.toModel(user: User) =
    ExpenseCategory(
        name = name,
        user = user
    )
