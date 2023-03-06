package com.msum.finance.plaid.data.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PlaidTransaction(
    val accountId: String,
    val amount: Double,
    val category: List<String?>,
    val categoryId: String?,
    val date: String,
    val plaidLocation: PlaidLocation?,
    val name: String,
    val merchantName: String?,
    val pending: Boolean,
    val transactionId: String,
    val paymentChannel: String
)
