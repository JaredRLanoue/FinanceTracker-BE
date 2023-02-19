package com.msum.finance.user.models.request

data class AuthenticationRequest(
    val email: String,
    val password: String
)
