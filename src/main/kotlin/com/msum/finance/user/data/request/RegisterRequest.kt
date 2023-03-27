package com.msum.finance.user.data.request

import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.model.Role
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    var password: String
) {
    init {
        validate(this) {
            validate(RegisterRequest::email)
                .isEmail()
                .isNotNull()
                .isNotEmpty()
            validate(RegisterRequest::password)
                .isNotNull()
                .isNotEmpty()
            validate(RegisterRequest::firstName)
                .isNotNull()
                .isNotEmpty()
            validate(RegisterRequest::lastName)
                .isNotNull()
                .isNotEmpty()
        }
    }
}

fun RegisterRequest.createUser() = UserEntity(
    firstName = firstName,
    lastName = lastName,
    loginEmail = email,
    loginPassword = password,
    role = Role.USER
)
