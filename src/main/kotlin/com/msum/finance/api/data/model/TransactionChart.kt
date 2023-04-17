package com.msum.finance.api.data.model

import java.math.BigDecimal
import java.time.Instant

data class TransactionChart(
    val date: Instant,
    val value: BigDecimal
)

data class TransactionChartList(
    val expenses: List<TransactionChart>,
    val incomes: List<TransactionChart>
)

data class TransactionChartView(
    val charts: TransactionChartList
)
