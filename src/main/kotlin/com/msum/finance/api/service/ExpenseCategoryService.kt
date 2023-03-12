package com.msum.finance.api.service

import com.msum.finance.api.data.entity.ExpenseCategoryEntity
import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.ExpenseCategory
import com.msum.finance.api.data.model.toEntity
import com.msum.finance.api.data.request.CategoryRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.repository.CategoryRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExpenseCategoryService(
    @Autowired private val categoryRepository: CategoryRepository,
    @Autowired private val userService: UserService
) {
    // TODO: For now, I'm using just the income categories. In the future I will add expense categories.
    fun create(user: User, request: CategoryRequest) {
        if (categoryRepository.findByUserIdAndName(user.id, request.name) != null) {
            throw Exception("Category already exists")
        }
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        categoryRepository.save(request.toModel(userData).toEntity())
    }

    fun findAll(user: User): List<ExpenseCategory> {
        return categoryRepository.findAllByUserId(user.id).map { it.toModel() }
    }

    fun findById(user: User, accountId: UUID): ExpenseCategory? {
        return categoryRepository.findByUserIdAndId(user.id, accountId)?.toModel()
    }

    fun deleteByIdIfNotInUse(user: User, categoryId: UUID) {
        categoryRepository.deleteByUserIdAndId(user.id, categoryId)
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
            categoryRepository.save(ExpenseCategoryEntity(name = category, user = user.toEntity()))
        }
    }
}
