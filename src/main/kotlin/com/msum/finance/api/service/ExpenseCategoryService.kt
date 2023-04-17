package com.msum.finance.api.service

import com.msum.finance.api.data.entity.ExpenseCategoryEntity
import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.CategoriesView
import com.msum.finance.api.data.model.Category
import com.msum.finance.api.data.model.CategoryTotal
import com.msum.finance.api.data.model.toExpenseCategoryEntity
import com.msum.finance.api.data.request.CategoryRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.event.NetWorthEvent
import com.msum.finance.api.repository.ExpenseCategoryRepository
import com.msum.finance.api.repository.ExpenseRepository
import com.msum.finance.common.utility.SortingUtils
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class ExpenseCategoryService(
    @Autowired private val expenseCategoryRepository: ExpenseCategoryRepository,
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val userService: UserService,
    @Autowired private val eventPublisher: ApplicationEventPublisher
) {
    fun create(user: User, request: CategoryRequest) {
        if (expenseCategoryRepository.findByUserIdAndName(user.id, request.name) != null) {
            throw Exception("Category already exists")
        } // maybe remove for full freedom on front end?
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        expenseCategoryRepository.save(request.toModel(userData).toExpenseCategoryEntity())
        saveNewNetWorth(user) // maybe not needed here since no expenses or incomes are associated, but leaving for now
    }

    fun findAll(user: User): List<Category> {
        return expenseCategoryRepository.findAllByUserId(user.id).map { it.toModel() }.sortedBy { it.createdAt }
    }

    fun findById(user: User, accountId: UUID): Category? {
        return expenseCategoryRepository.findByUserIdAndId(user.id, accountId)?.toModel()
    }

    fun findByUserIdAndName(user: User, categoryName: String): Category? {
        return expenseCategoryRepository.findByUserIdAndName(user.id, categoryName)?.toModel()
    }

    fun deleteById(user: User, categoryId: UUID) {
        expenseCategoryRepository.deleteById(categoryId)
        saveNewNetWorth(user)
    }

    fun update(user: User, request: CategoryRequest, categoryId: UUID) {
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        expenseCategoryRepository.save(request.toModel(userData).apply { id = categoryId }.toExpenseCategoryEntity())
        saveNewNetWorth(user)
    }

    fun saveDefaults(user: User) {
        val defaultCategories = listOf(
            "Housing" to BigDecimal(1000),
            "Transportation" to BigDecimal(400),
            "Food and Groceries" to BigDecimal(300),
            "Entertainment" to BigDecimal(150),
            "Health and Wellness" to BigDecimal(100)
        )

        defaultCategories.forEach { (name, monthlyBudget) ->
            expenseCategoryRepository.save(ExpenseCategoryEntity(name = name, user = user.toEntity(), monthlyBudget = monthlyBudget))
        }
    }

    fun saveNewNetWorth(user: User) {
        val updatedUserData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        eventPublisher.publishEvent(NetWorthEvent(updatedUserData))
    }

    fun calculateTotals(user: User, sortMethod: String): CategoriesView {
        val categories = findAll(user)
        val (startDate, endDate) = SortingUtils.getSortMethodRange(sortMethod)
        val expenses = expenseRepository.findAllExpensesByUserIdAndDateRange(user.id, startDate, endDate)
        val listOfCategoryTotal: MutableList<CategoryTotal> = mutableListOf()

        for (category in categories) {
            var expenseTotal = BigDecimal.ZERO
            for (expense in expenses.filter { it.category.id == category.id }) {
                expenseTotal += expense.amount
            }
            val categoryTotal = CategoryTotal(category.name, expenseTotal, category.monthlyBudget)
            listOfCategoryTotal += categoryTotal
        }

        return CategoriesView(listOfCategoryTotal)
    }
}
