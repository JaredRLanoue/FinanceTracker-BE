package com.msum.finance.plaid.api

import com.msum.finance.plaid.data.model.PlaidResponse
import com.msum.finance.plaid.data.request.PlaidRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PlaidApiInterface {
    @POST("/transactions/get")
    fun getTransactions(@Body request: PlaidRequestBody): Call<PlaidResponse>
}
