package com.msum.finance.user.data.request

import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class AuthenticationRequest(
    val email: String,
    val password: String
) {
    init {
        validate(this) {
            validate(AuthenticationRequest::email)
                .isEmail()
                .isNotNull()
                .isNotEmpty()
            validate(AuthenticationRequest::password)
                .isNotNull()
                .isNotEmpty()
        }
    }
}
