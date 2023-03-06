package com.msum.finance.plaid.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.msum.finance.common.configuration.Env
import com.msum.finance.plaid.api.PlaidApiInterface
import com.msum.finance.plaid.data.model.PlaidTransaction
import com.msum.finance.plaid.data.request.PlaidRequestBody
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

@Service
class PlaidService {
    private lateinit var plaidClient: PlaidApi
    private final val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://development.plaid.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    private val plaidApi = retrofit.create(PlaidApiInterface::class.java)
    private val plaidRequestBody = PlaidRequestBody(
        clientId = Env.PLAID_CLIENT_ID,
        secret = Env.PLAID_SECRET_KEY,
        accessToken = "access-sandbox-f908a334-f748-41a6-99f4-38e3bcb40143",
        startDate = LocalDate.EPOCH.toString(),
        endDate = LocalDate.now().toString()
    )

    fun getData(): List<PlaidTransaction>? {
        val response = plaidApi.getTransactions(plaidRequestBody).execute()
        if (response.isSuccessful) {
            response.body()
//            response.body()?.accounts?.forEach { transaction -> println(transaction.balances) }
            return response.body()?.plaidTransactions
        } else {
            throw Exception("Error: ${response.body()}")
        }
    }
}
