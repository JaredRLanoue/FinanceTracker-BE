package com.msum.finance.api.service

import com.msum.finance.api.data.entity.*
import com.msum.finance.api.repository.*
import com.msum.finance.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Service
class ExampleDataService(
    @Autowired private val repository: UserRepository,
    @Autowired private val expenseCategoryRepository: ExpenseCategoryRepository,
    @Autowired private val incomeCategoryRepository: IncomeCategoryRepository,
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val incomeRepository: IncomeRepository
) {
    // consider expanding this for testing purposes, add a much larger dataset for charting
    fun createExampleData(userId: UUID) {
        val user = repository.findById(userId).get()

        val expenseCategory = expenseCategoryRepository.save(
            ExpenseCategoryEntity(
                name = "Retail",
                user = user
            )
        )

        val incomeCategory = incomeCategoryRepository.save(
            IncomeCategoryEntity(
                name = "Investments",
                user = user
            )
        )

        val account = accountRepository.save(
            AccountEntity(
                user = user,
                name = "Chase Checking",
                type = "Checking",
                balance = BigDecimal(10000),
                startingBalance = BigDecimal(10000),
                expenses = listOf(),
                incomes = listOf()
            )
        )

        expenseRepository.save(
            ExpenseEntity(
                accountId = account.id,
                category = expenseCategory,
                amount = BigDecimal(250),
                description = "The Last of Us Collectors Edition",
                merchantName = "GameStop",
                pending = false,
                date = Instant.now(),
                user = user
            )
        )

        incomeRepository.save(
            IncomeEntity(
                accountId = account.id,
                category = incomeCategory,
                user = user,
                amount = BigDecimal(100),
                date = Instant.now(),
                payerName = "Bushel",
                description = "Biweekly income"
            )
        )
    }
}
