package com.msum.finance.user.data.request

import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.model.Role
import com.msum.finance.user.data.model.User

data class UpdateRequest(
    val firstName: String?,
    val lastName: String?,
    val email: String?
)

fun UpdateRequest.toUser(user: User) = UserEntity(
    firstName = firstName ?: user.firstName,
    lastName = lastName ?: user.lastName,
    loginEmail = (email ?: user.loginEmail).lowercase(),
    loginPassword = user.lastName,
    role = Role.USER
)
