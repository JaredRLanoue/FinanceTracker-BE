package com.msum.finance.api.service

import com.msum.finance.api.data.entity.*
import com.msum.finance.api.repository.*
import com.msum.finance.user.repository.UserRepository
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class ExampleDataService(
    @Autowired private val repository: UserRepository,
    @Autowired private val expenseCategoryRepository: ExpenseCategoryRepository,
    @Autowired private val incomeCategoryRepository: IncomeCategoryRepository,
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val incomeRepository: IncomeRepository,
    @Autowired private val logger: Logger
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

        try {
            accountRepository.save(
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

            accountRepository.save(
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

            accountRepository.save(
                AccountEntity(
                    user = user,
                    name = "US Bank Checking",
                    type = "Checking",
                    balance = BigDecimal(0),
                    startingBalance = BigDecimal(0),
                    expenses = listOf(),
                    incomes = listOf()
                )
            )

            accountRepository.save(
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

            accountRepository.save(
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
        } catch (e: Exception) {
            logger.info("Accounts already loaded - continuing to save transactions")
        }

        val sort = Sort.by(Sort.DEFAULT_DIRECTION, "balance")
        val accountList = accountRepository.findAllByUserId(sort, userId)

        val random = Random()
        val endDate = LocalDate.now()
        val startDate = endDate.minusYears(2)

        for (year in startDate.year..endDate.year) {
            val endMonth = if (year == endDate.year) endDate.monthValue else 12
            for (month in 1..endMonth) {
                val date = LocalDate.of(year, month, 1)
                for (i in 1..7) {
                    expenseRepository.save(
                        ExpenseEntity(
                            accountId = accountList.random().id,
                            category = expenseCategories.random(),
                            amount = (random.nextInt(5, 151) + random.nextDouble(0.00, 1.00)).toBigDecimal()
                                .setScale(2, RoundingMode.HALF_UP),
                            description = expenseDescriptions.random(),
                            merchantName = merchantNames.random(),
                            pending = false,
                            date = date.atTime(
                                (Math.random() * 23).toInt(),
                                (Math.random() * 59).toInt(),
                                (Math.random() * 59).toInt(),
                                0
                            ).toInstant(ZoneOffset.UTC),
                            user = user
                        )
                    )
                }
            }
        }

        for (year in startDate.year..endDate.year) {
            val endMonth = if (year == endDate.year) endDate.monthValue else 12
            for (month in 1..endMonth) {
                val date = LocalDate.of(year, month, 1)
                for (i in 1..1) {
                    incomeRepository.save(
                        IncomeEntity(
                            accountId = accountList.random().id,
                            amount = (random.nextInt(950, 1501) + random.nextDouble(0.00, 1.00)).toBigDecimal()
                                .setScale(2, RoundingMode.HALF_UP),
                            payerName = payerNames.random(),
                            description = incomeDescriptions.random(),
                            date = date.atTime(
                                (Math.random() * 23).toInt(),
                                (Math.random() * 59).toInt(),
                                (Math.random() * 59).toInt(),
                                0
                            ).toInstant(ZoneOffset.UTC),
                            user = user,
                            category = incomeCategories[1]
                        )
                    )
                }
            }
        }

        val today = LocalDate.now()
        val oneWeekAgo = today.minus(1, ChronoUnit.WEEKS)

        for (i in 1..5) {
            val date = oneWeekAgo.plusDays(random.nextLong(0, ChronoUnit.DAYS.between(oneWeekAgo, today) + 1))
            expenseRepository.save(
                ExpenseEntity(
                    accountId = accountList.random().id,
                    category = expenseCategories[i - 1],
                    amount = (random.nextInt(5, 151) + random.nextDouble(0.00, 1.00)).toBigDecimal()
                        .setScale(2, RoundingMode.HALF_UP),
                    description = expenseDescriptions.random(),
                    merchantName = merchantNames.random(),
                    pending = false,
                    date = date.atTime(
                        (Math.random() * 23).toInt(),
                        (Math.random() * 59).toInt(),
                        (Math.random() * 59).toInt(),
                        0
                    ).toInstant(ZoneOffset.UTC),
                    user = user
                )
            )
            incomeRepository.save(
                IncomeEntity(
                    accountId = accountList.random().id,
                    amount = (random.nextInt(100, 301) + random.nextDouble(0.00, 1.00)).toBigDecimal()
                        .setScale(2, RoundingMode.HALF_UP),
                    payerName = payerNames.random(),
                    description = incomeDescriptions.random(),
                    date = date.atTime(
                        (Math.random() * 23).toInt(),
                        (Math.random() * 59).toInt(),
                        (Math.random() * 59).toInt(),
                        0
                    ).toInstant(ZoneOffset.UTC),
                    user = user,
                    category = incomeCategories.random()
                )
            )
        }

//        val date = oneWeekAgo.plusDays(random.nextLong(0, ChronoUnit.DAYS.between(oneWeekAgo, today) + 1))
//        incomeRepository.save(
//            IncomeEntity(
//                accountId = accountList.random().id,
//                amount = (random.nextInt(950, 1501) + random.nextDouble(0.00, 1.00)).toBigDecimal()
//                    .setScale(2, RoundingMode.HALF_UP),
//                payerName = payerNames.random(),
//                description = incomeDescriptions.random(),
//                date = date.atTime(
//                    (Math.random() * 23).toInt(),
//                    (Math.random() * 59).toInt(),
//                    (Math.random() * 59).toInt(),
//                    0
//                ).toInstant(ZoneOffset.UTC),
//                user = user,
//                category = incomeCategories.random()
//            )
//        )
    }
}
