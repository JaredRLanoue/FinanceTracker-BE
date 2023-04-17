package com.msum.finance.api.service

import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.*
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.event.NetWorthEvent
import com.msum.finance.api.repository.ExpenseRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExpenseService(
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val userService: UserService,
    @Autowired private val expenseCategoryService: ExpenseCategoryService,
    @Autowired private val eventPublisher: ApplicationEventPublisher,
    @Autowired private val accountService: AccountService
) {
//    fun create(user: User, request: ExpenseRequest) {
//        userService.checkAccountExistsForUser(request.accountId, user)
//        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
//        val categoryData =
//            expenseCategoryService.findById(user, request.categoryId) ?: throw Exception("Category doesn't exist")
//        val accountData =
//            accountService.findById(user, request.accountId) ?: throw Exception("Account doesn't exist")
//
//        expenseRepository.save(request.toModel(userData, categoryData, accountData).toExpenseEntity())
//        eventPublisher.publishEvent(NetWorthEvent(user))
//    }

    fun findAll(user: User): List<Expense> {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return expenseRepository.findAllByUserId(user.id).map { it.toModel() }
    }

    fun findById(user: User, expenseId: UUID): Expense? {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return expenseRepository.findByUserIdAndId(user.id, expenseId)?.toModel()
    }

    fun deleteById(user: User, expenseId: UUID) {
        expenseRepository.deleteById(expenseId)
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

//    fun update(user: User, request: ExpenseRequest, expenseId: UUID) {
//        userService.checkAccountExistsForUser(request.accountId, user)
//        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
//        val expenseData = expenseRepository.findByUserIdAndId(user.id, expenseId)?.toModel()
//            ?: throw Exception("Account doesn't exist")
//        val categoryData =
//            expenseCategoryService.findById(user, request.categoryId) ?: throw Exception("Category doesn't exist")
//        val accountData =
//            accountService.findById(user, request.accountId) ?: throw Exception("Category doesn't exist")
//        eventPublisher.publishEvent(NetWorthEvent(user))
//        expenseRepository.save(
//            request.toModel(userData, categoryData).apply { id = expenseData.id } ).toModel()
//        )
//    }
}
