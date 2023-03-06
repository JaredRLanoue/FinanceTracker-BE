package com.msum.finance.plaid.data.model

data class PlaidPaymentMeta(
    val byOrderOf: String?,
    val payee: String?,
    val payer: String?,
    val paymentMethod: String?,
    val paymentProcessor: String?,
    val ppdId: String?,
    val reason: String?,
    val referenceNumber: String?
)
