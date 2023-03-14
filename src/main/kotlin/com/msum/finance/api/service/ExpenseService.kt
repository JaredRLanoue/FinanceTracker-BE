package com.msum.finance.api.service

import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.CategoryTotal
import com.msum.finance.api.data.model.Expense
import com.msum.finance.api.data.model.ExpenseCategoriesTotal
import com.msum.finance.api.data.model.toEntity
import com.msum.finance.api.data.request.ExpenseRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.event.NetWorthEvent
import com.msum.finance.api.repository.ExpenseRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class ExpenseService(
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val userService: UserService,
    @Autowired private val expenseCategoryService: ExpenseCategoryService,
    @Autowired private val eventPublisher: ApplicationEventPublisher
) {
    fun create(user: User, request: ExpenseRequest) {
        userService.checkAccountExistsForUser(request.accountId, user)
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        val categoryData =
            expenseCategoryService.findById(user, request.categoryId) ?: throw Exception("Category doesn't exist")

        expenseRepository.save(request.toModel(userData, categoryData).toEntity())
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    fun findAllCategoryTotals(user: User): ExpenseCategoriesTotal {
        val results = expenseRepository.findCategoryTotalsByUser(user.id)
        val categories = results.map { result ->
            CategoryTotal(
                category = result["category"] as String,
                total = (result["total"] as BigDecimal).negate() // find better solution here, and below can be cleaned up too
            )
        }
        var totalExpenses = BigDecimal.ZERO
        user.accounts.forEach { account ->
            totalExpenses += account?.expenses?.sumOf { it.amount }?.negate() ?: BigDecimal.ZERO
        }
        return ExpenseCategoriesTotal(categories = categories, totalExpenses = totalExpenses)
    }

    fun findAllByPage(user: User, pageNumber: Int, pageSize: Int, sortField: String, sortOrder: String): Page<Expense> {
        val pageable = if (sortOrder == "ascending") {
            PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending())
        } else {
            PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending())
        }
        return expenseRepository.findByAccountId(user.id, pageable).map { it.toModel() }
    }

    fun findAll(user: User): List<Expense> {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return expenseRepository.findAllByUserId(user.id).map { it.toModel() }
    }

    fun findById(user: User, expenseId: UUID): Expense? {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return expenseRepository.findByUserIdAndId(user.id, expenseId)?.toModel()
    }

    fun deleteById(user: User, expenseId: UUID) {
        expenseRepository.deleteByUserIdAndId(user.id, expenseId)
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    fun update(user: User, request: ExpenseRequest, expenseId: UUID) {
        userService.checkAccountExistsForUser(request.accountId, user)
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        val expenseData = expenseRepository.findByUserIdAndId(user.id, expenseId)?.toModel()
            ?: throw Exception("Account doesn't exist")
        val categoryData =
            expenseCategoryService.findById(user, request.categoryId) ?: throw Exception("Category doesn't exist")

        eventPublisher.publishEvent(NetWorthEvent(user))
        expenseRepository.save(request.toModel(userData, categoryData).apply { id = expenseData.id }.toEntity())
    }
}
