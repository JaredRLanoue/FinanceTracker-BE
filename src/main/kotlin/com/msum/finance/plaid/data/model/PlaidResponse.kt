package com.msum.finance.plaid.data.model

data class PlaidResponse(
    val plaidAccounts: List<PlaidAccount>,
    val plaidTransactions: List<PlaidTransaction>,
//    val item: Item,
    val totalTransactions: Int,
    val requestId: String
)
