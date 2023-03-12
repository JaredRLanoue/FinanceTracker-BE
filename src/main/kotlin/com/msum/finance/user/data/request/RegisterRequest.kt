package com.msum.finance.user.data.request

import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.model.Role

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    var password: String
)

fun RegisterRequest.createUser() = UserEntity(
    firstName = firstName,
    lastName = lastName,
    loginEmail = email,
    loginPassword = password,
    role = Role.USER
)
