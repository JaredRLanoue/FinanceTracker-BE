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

fun RegisterRequest.createUser(request: RegisterRequest, encoder: PasswordEncoder): UserEntity {
    return UserEntity(
        firstName = request.firstName,
        lastName = request.lastName,
        loginEmail = request.email,
        loginPassword = encoder.encode(request.password),
        role = Role.USER
    )
}
