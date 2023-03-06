package com.msum.finance.plaid.data.model

data class PlaidLocation(
    val address: String?,
    val city: String?,
    val region: String?,
    val postalCode: String?,
    val country: String?,
    val lat: Double?,
    val lon: Double?,
    val storeNumber: String?
)
