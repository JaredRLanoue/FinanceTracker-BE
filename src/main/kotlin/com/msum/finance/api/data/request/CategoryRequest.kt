package com.msum.finance.api.data.request

import com.msum.finance.api.data.model.Category
import com.msum.finance.user.data.model.User
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class CategoryRequest(
    val name: String
) {
    init {
        validate(this) {
            validate(CategoryRequest::name)
                .isNotNull()
                .isNotEmpty()
        }
    }
}

fun CategoryRequest.toModel(user: User) =
    Category(
        name = name,
        user = user
    )
