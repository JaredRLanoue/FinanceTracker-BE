package com.msum.finance.user.models.request

import com.msum.finance.user.models.Role
import com.msum.finance.user.models.entity.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

fun RegisterRequest.createUser(encoder: PasswordEncoder) = UserEntity(
    firstName = firstName,
    lastName = lastName,
    loginEmail = email,
    loginPassword = encoder.encode(password),
    role = Role.USER
)
