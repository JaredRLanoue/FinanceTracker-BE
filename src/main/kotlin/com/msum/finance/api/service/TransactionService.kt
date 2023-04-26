package com.msum.finance.api.service

import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.*
import com.msum.finance.api.data.request.TransactionRequest
import com.msum.finance.api.data.request.toExpenseModel
import com.msum.finance.api.data.request.toIncomeModel
import com.msum.finance.api.event.NetWorthEvent
import com.msum.finance.api.repository.*
import com.msum.finance.user.data.model.User
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.*
import java.util.*

@Service
class TransactionService(
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val expenseCategoryRepository: ExpenseCategoryRepository,
    @Autowired private val incomeRepository: IncomeRepository,
    @Autowired private val incomeCategoryRepository: IncomeCategoryRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val eventPublisher: ApplicationEventPublisher
) {
    fun findAllTransactionsByQuery(sortMethod: String, type: String?, user: User): TransactionList {
        val sort: Sort = when (sortMethod) {
            "newest" -> Sort.by(Sort.Direction.DESC, "date")
            "oldest" -> Sort.by(Sort.Direction.ASC, "date")
            "largest" -> Sort.by(Sort.Direction.DESC, "amount")
            "smallest" -> Sort.by(Sort.Direction.ASC, "amount")
            else -> Sort.by(Sort.Direction.DESC, "date")
        }

        val meta = TransactionMeta(
            totalExpenses = BigDecimal.ZERO,
            totalIncomes = BigDecimal.ZERO,
            averageTransaction = BigDecimal.ZERO
        )

        return when (type) {
            "expenses" -> {
                val expenses = expenseRepository.findAllByUserId(sort, user.id).map { it.toModel().toTransaction() }
                TransactionList(data = expenses, meta = meta)
            }

            "incomes" -> {
                val incomes = incomeRepository.findAllByUserId(sort, user.id).map { it.toModel().toTransaction() }
                TransactionList(data = incomes, meta = meta)
            }

            else -> {
                val incomes =
                    incomeRepository.findAllByUserId(Sort.unsorted(), user.id).map { it.toModel().toTransaction() }
                val expenses =
                    expenseRepository.findAllByUserId(Sort.unsorted(), user.id).map { it.toModel().toTransaction() }
                val transactions = mutableListOf<Transaction>().apply {
                    addAll(incomes)
                    addAll(expenses)
                }
                transactions.sortWith(
                    compareBy<Transaction> {
                        when (sortMethod) {
                            "newest" -> it.date
                            "oldest" -> it.date
                            "largest" -> it.amount
                            "smallest" -> it.amount
                            else -> it.date
                        }
                    }.run {
                        if (sortMethod == "newest" || sortMethod == "largest") reversed() else this
                    }
                )
                TransactionList(data = transactions, meta = meta)
            }
        }
    }

    @Transactional
    fun delete(user: User, type: String, transactionId: UUID) {
        when (type) {
            "Expense" -> {
                if (expenseRepository.findByUserIdAndId(user.id, transactionId) == null) {
                    throw Exception("Expense not found")
                }
                expenseRepository.deleteById(transactionId)
                eventPublisher.publishEvent(NetWorthEvent(user))
            }

            "Income" -> {
                if (incomeRepository.findByUserIdAndId(user.id, transactionId) == null) {
                    throw Exception("Expense not found")
                }
                incomeRepository.deleteById(transactionId)
                eventPublisher.publishEvent(NetWorthEvent(user))
            }

            else -> throw Exception("No type specified")
        }
    }

    fun create(user: User, request: TransactionRequest) {
        val account = accountRepository.findAccountById(UUID.fromString(request.accountId))?.toModel()
            ?: throw Exception("No category found")
        when (request.type) {
            "Expense" -> {
                val category = expenseCategoryRepository.findByUserIdAndId(user.id, UUID.fromString(request.categoryId))?.toModel()
                    ?: throw Exception("No category found")
                expenseRepository.save(request.toExpenseModel(user, category, account).toExpenseEntity())
                eventPublisher.publishEvent(NetWorthEvent(user))
            }

            "Income" -> {
                val category = incomeCategoryRepository.findByUserIdAndId(user.id, UUID.fromString(request.categoryId))?.toModel()
                    ?: throw Exception("No category found")
                incomeRepository.save(request.toIncomeModel(user, category, account).toIncomeEntity())
                eventPublisher.publishEvent(NetWorthEvent(user))
            }

            else -> throw Exception("No type specified")
        }
    }

    @Transactional
    fun update(user: User, request: TransactionRequest, transactionId: UUID) {
        val account = accountRepository.findAccountById(UUID.fromString(request.accountId))?.toModel() ?: throw Exception("No category found")
        if (request.type != account.type) {
            if (request.type == "Expense") {
                incomeRepository.deleteById(account.id)
            } else if (request.type == "Income") {
                expenseRepository.deleteById(account.id)
            }
        }

        when (request.type) {
            "Expense" -> {
                val category = expenseCategoryRepository.findByUserIdAndId(user.id, UUID.fromString(request.categoryId))?.toModel()
                    ?: throw Exception("No category found")
                expenseRepository.save(
                    request.toExpenseModel(user, category, account).apply { id = transactionId }
                        .toExpenseEntity()
                )
                eventPublisher.publishEvent(NetWorthEvent(user))
            }

            "Income" -> {
                val category = incomeCategoryRepository.findByUserIdAndId(user.id, UUID.fromString(request.categoryId))?.toModel()
                    ?: throw Exception("No category found")
                incomeRepository.save(
                    request.toIncomeModel(user, category, account).apply { id = transactionId }
                        .toIncomeEntity()
                )
                eventPublisher.publishEvent(NetWorthEvent(user))
            }

            else -> throw Exception("No type specified")
        }
    }

    fun calculateExpensesOverTime(user: User): List<TransactionChart> {
        val expenses = expenseRepository.findAllByUserId(user.id).map { it.toModel() }
        val expensesByMonth = expenses.groupBy { it.date.atZone(ZoneOffset.UTC).toLocalDate().let { date -> YearMonth.of(date.year, date.month) } }
        val transactionChartList = mutableListOf<TransactionChart>()

        for ((month, expensesOnMonth) in expensesByMonth.entries.sortedBy { it.key }) {
            val totalOnMonth = expensesOnMonth.map { it.amount }.reduce { acc, amount -> acc + amount }
            val firstDayOfMonth = month.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant()
            transactionChartList.add(TransactionChart(firstDayOfMonth, totalOnMonth))
        }
        return transactionChartList
    }

    fun calculateIncomesOverTime(user: User): List<TransactionChart> {
        val incomes = incomeRepository.findAllByUserId(user.id).map { it.toModel() }
        val incomesByMonth = incomes.groupBy { it.date.atZone(ZoneOffset.UTC).toLocalDate().let { date -> YearMonth.of(date.year, date.month) } }
        val transactionChartList = mutableListOf<TransactionChart>()

        for ((month, incomesOnMonth) in incomesByMonth.entries.sortedBy { it.key }) {
            val totalOnMonth = incomesOnMonth.map { it.amount }.reduce { acc, amount -> acc + amount }
            val firstDayOfMonth = month.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant()
            transactionChartList.add(TransactionChart(firstDayOfMonth, totalOnMonth))
        }
        return transactionChartList
    }

    fun calculateCharts(user: User): TransactionChartList {
        val expenses = calculateExpensesOverTime(user)
        val incomes = calculateIncomesOverTime(user)
        return TransactionChartList(expenses = expenses, incomes = incomes)
    }
}
