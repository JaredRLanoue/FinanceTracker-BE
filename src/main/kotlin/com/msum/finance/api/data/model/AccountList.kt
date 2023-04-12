package com.msum.finance.api.data.model

import com.msum.finance.api.data.view.AccountView
import java.math.BigDecimal

data class AccountList(
    val data: List<AccountView> = listOf(),
    val meta: AccountMeta
)

data class AccountMeta(
    val total: Int,
    val average: BigDecimal,
    val netWorth: BigDecimal
)
