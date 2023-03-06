package com.msum.finance.plaid.data.request

data class PlaidRequestBody(
    val clientId: String,
    val secret: String,
    val accessToken: String,
    val startDate: String,
    val endDate: String
)
