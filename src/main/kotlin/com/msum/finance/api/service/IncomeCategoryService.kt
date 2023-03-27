package com.msum.finance.api.service

import com.msum.finance.api.data.entity.IncomeCategoryEntity
import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.Category
import com.msum.finance.api.data.model.toIncomeCategoryEntity
import com.msum.finance.api.data.request.CategoryRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.repository.IncomeCategoryRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class IncomeCategoryService(
    @Autowired private val incomeCategoryRepository: IncomeCategoryRepository,
    @Autowired private val userService: UserService
) {
    fun create(user: User, request: CategoryRequest) {
        if (incomeCategoryRepository.findByUserIdAndName(user.id, request.name) != null) {
            throw Exception("Category already exists")
        }
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        incomeCategoryRepository.save(request.toModel(userData).toIncomeCategoryEntity())
    }

    fun findAll(user: User): List<Category> {
        return incomeCategoryRepository.findAllByUserId(user.id).map { it.toModel() }
    }

    fun findById(user: User, accountId: UUID): Category? {
        return incomeCategoryRepository.findByUserIdAndId(user.id, accountId)?.toModel()
    }

    fun deleteByIdIfNotInUse(user: User, categoryId: UUID) {
        incomeCategoryRepository.deleteByUserIdAndId(user.id, categoryId)
    }

    fun saveDefaults(user: User) {
        val defaultCategories = listOf(
            "Wages",
            "Rental",
            "Investment Gains",
            "Business",
            "Gifts",
            "Refunds and Reimbursements",
            "Freelance"
        )

        defaultCategories.forEach { category ->
            incomeCategoryRepository.save(IncomeCategoryEntity(name = category, user = user.toEntity()))
        }
    }
}
