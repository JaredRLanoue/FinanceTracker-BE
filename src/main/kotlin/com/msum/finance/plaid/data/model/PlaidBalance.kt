package com.msum.finance.plaid.data.model

data class PlaidBalance(
    val available: Int,
    val current: Float,
    val isoCurrencyCode: String,
    val limit: String?,
    val unofficialCurrencyCode: String?
)
