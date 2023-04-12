package com.msum.finance.api.service

import com.msum.finance.api.data.entity.*
import com.msum.finance.api.repository.*
import com.msum.finance.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZoneOffset
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
    fun createExampleData(userId: UUID) {
        val user = repository.findById(userId).get()
        val expenseCategories = expenseCategoryRepository.findAllByUserId(userId)
        val incomeCategories = incomeCategoryRepository.findAllByUserId(userId)

        val payerNames = listOf(
            "Google Inc.",
            "Amazon Web Services",
            "Microsoft Corporation",
            "Apple Inc.",
            "Facebook Inc."
        )

        val merchantNames = listOf(
            "Amazon",
            "Walmart",
            "Starbucks",
            "Best Buy",
            "Costco"
        )

        val expenseDescriptions = listOf(
            "Groceries at ${merchantNames.random()}",
            "Shopping at ${merchantNames.random()}",
            "Online shopping at ${merchantNames.random()}",
            "Gas at ${merchantNames.random()}"
        )

        val incomeDescriptions = listOf(
            "Paycheck from ${payerNames.random()}",
            "Bonus from ${payerNames.random()}",
            "Dividend payment from ${payerNames.random()}",
            "Interest payment from ${payerNames.random()}"
        )

        val account1 = accountRepository.save(
            AccountEntity(
                user = user,
                name = "Chase Checking",
                type = "Checking",
                balance = BigDecimal(0),
                startingBalance = BigDecimal(0),
                expenses = listOf(),
                incomes = listOf()
            )
        )

        val account2 = accountRepository.save(
            AccountEntity(
                user = user,
                name = "Discover Savings",
                type = "Savings",
                balance = BigDecimal(0),
                startingBalance = BigDecimal(0),
                expenses = listOf(),
                incomes = listOf()
            )
        )

        val account3 = accountRepository.save(
            AccountEntity(
                user = user,
                name = "US Bank Credit Card",
                type = "Credit",
                balance = BigDecimal(0),
                startingBalance = BigDecimal(0),
                expenses = listOf(),
                incomes = listOf()
            )
        )

        val account4 = accountRepository.save(
            AccountEntity(
                user = user,
                name = "Wells Fargo Checking",
                type = "Checking",
                balance = BigDecimal(0),
                startingBalance = BigDecimal(0),
                expenses = listOf(),
                incomes = listOf()
            )
        )

        val account5 = accountRepository.save(
            AccountEntity(
                user = user,
                name = "American Express Savings",
                type = "Savings",
                balance = BigDecimal(0),
                startingBalance = BigDecimal(0),
                expenses = listOf(),
                incomes = listOf()
            )
        )

        val accountList = listOf(account1, account2, account3, account4, account5)

        val random = Random()
        val endDate = LocalDate.now()
        val startDate = endDate.minusYears(2)

        for (year in startDate.year..endDate.year) {
            for (month in 1..12) {
                val date = LocalDate.of(year, month, 1)
                for (i in 1..20) {
                    expenseRepository.save(
                        ExpenseEntity(
                            accountId = accountList.random().id,
                            category = expenseCategories.random(),
                            amount = random.nextInt(150).toBigDecimal(),
                            description = expenseDescriptions.random(),
                            merchantName = merchantNames.random(),
                            pending = false,
                            date = date.atStartOfDay().toInstant(ZoneOffset.UTC),
                            user = user
                        )
                    )
                }
            }
        }

        for (year in startDate.year..endDate.year) {
            for (month in 1..12) {
                val date = LocalDate.of(year, month, 1)
                for (i in 1..2) {
                    incomeRepository.save(
                        IncomeEntity(
                            accountId = accountList.random().id,
                            amount = random.nextInt(1000, 2500).toBigDecimal(),
                            payerName = payerNames.random(),
                            description = incomeDescriptions.random(),
                            date = date.atStartOfDay().toInstant(ZoneOffset.UTC),
                            user = user,
                            category = incomeCategories.random()
                        )
                    )
                }
            }
        }
    }
}
