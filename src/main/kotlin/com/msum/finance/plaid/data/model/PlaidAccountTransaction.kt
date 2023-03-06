package com.msum.finance.plaid.data.model

data class PlaidAccountTransaction(
    val accountId: String,
    val balances: PlaidBalance,
    val mask: String,
    val name: String,
    val officialName: String,
    val subtype: String,
    val type: String
)
