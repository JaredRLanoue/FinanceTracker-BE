package com.msum.finance.api.service

import com.msum.finance.api.data.entity.ExpenseCategoryEntity
import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.ExpenseCategory
import com.msum.finance.api.data.model.toEntity
import com.msum.finance.api.data.request.ExpenseCategoryRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.repository.ExpenseCategoryRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExpenseCategoryService(
    @Autowired private val expenseCategoryRepository: ExpenseCategoryRepository,
    @Autowired private val userService: UserService
) {
    // TODO: For now, I'm using just the expense categories. In the future I will add income categories.
    fun create(user: User, request: ExpenseCategoryRequest) {
        if (expenseCategoryRepository.findByUserIdAndName(user.id, request.name) != null) {
            throw Exception("Category already exists")
        }
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        expenseCategoryRepository.save(request.toModel(userData).toEntity())
    }

    fun findAll(user: User): List<ExpenseCategory> {
        return expenseCategoryRepository.findAllByUserId(user.id).map { it.toModel() }
    }

    fun findById(user: User, accountId: UUID): ExpenseCategory? {
        return expenseCategoryRepository.findByUserIdAndId(user.id, accountId)?.toModel()
    }

    fun deleteByIdIfNotInUse(user: User, categoryId: UUID) {
        expenseCategoryRepository.deleteByUserIdAndId(user.id, categoryId)
    }

    fun saveDefaults(user: User) {
        val defaultCategories = listOf(
            "Housing",
            "Transportation",
            "Food and Dining",
            "Entertainment",
            "Personal Care",
            "Health Care",
            "Debt"
        )

        defaultCategories.forEach { category ->
            expenseCategoryRepository.save(ExpenseCategoryEntity(name = category, user = user.toEntity()))
        }
    }
}
