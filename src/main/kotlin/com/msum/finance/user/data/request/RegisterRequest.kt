package com.msum.finance.user.data.request

import com.msum.finance.user.data.Role
import com.msum.finance.user.data.entity.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

fun RegisterRequest.createUser(encoder: PasswordEncoder) = UserDetails(
    firstName = firstName,
    lastName = lastName,
    loginEmail = email,
    loginPassword = encoder.encode(password),
    role = Role.USER
)
