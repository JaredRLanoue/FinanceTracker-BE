package com.msum.finance.api.service

import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.*
import com.msum.finance.api.data.request.IncomeRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.event.NetWorthEvent
import com.msum.finance.api.repository.IncomeRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class IncomeService(
    @Autowired private val incomeRepository: IncomeRepository,
    @Autowired private val userService: UserService,
    @Autowired private val incomeCategoryService: IncomeCategoryService,
    @Autowired private val eventPublisher: ApplicationEventPublisher,
    @Autowired private val accountService: AccountService
) {
    fun create(user: User, request: IncomeRequest) {
        userService.checkAccountExistsForUser(request.accountId, user)
        val categoryData =
            incomeCategoryService.findById(user, request.categoryId) ?: throw Exception("Category doesn't exist")
//        val accountData =
//            accountService.findById(user, request.categoryId) ?: throw Exception("Account doesn't exist")

        incomeRepository.save(request.toModel(user, categoryData).toIncomeEntity())
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    fun findAllCategoryTotals(user: User): CategoriesView {
        val categories = incomeRepository.findCategoryTotalsByUser(user.id)
            .map { result ->
                CategoryTotal(
                    category = result["category"] as String,
                    total = result["total"] as BigDecimal,
                    budget = BigDecimal.ZERO
                )
            }
//        val totalExpenses = user.accounts.sumOf { account ->
//            account?.incomes?.sumOf { it.amount } ?: BigDecimal.ZERO
//        }

        return CategoriesView(categories = categories)
    }

    fun findAll(user: User): List<Income> {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return incomeRepository.findAllByUserId(user.id).map { it.toModel() }
    }

    fun findById(user: User, accountId: UUID): Income? {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return incomeRepository.findByUserIdAndId(user.id, accountId)?.toModel()
    }

    fun update(user: User, request: IncomeRequest, incomeId: UUID) {
        userService.checkAccountExistsForUser(request.accountId, user)
        val incomeData =
            incomeRepository.findByUserIdAndId(user.id, incomeId)?.toModel() ?: throw Exception("Account doesn't exist")
        val categoryData =
            incomeCategoryService.findById(user, request.categoryId) ?: throw Exception("Category doesn't exist")
//        val accountData =
//            accountService.findById(user, request.categoryId) ?: throw Exception("Account doesn't exist")

        eventPublisher.publishEvent(NetWorthEvent(user))
        incomeRepository.save(request.toModel(user, categoryData).apply { id = incomeData.id }.toIncomeEntity())
    }

    // TODO: Can potentially just put this into one function, with option to choose date range and sort order
    fun findAllOrderByDescending(user: User): List<Income> {
        return incomeRepository.findAllByUserIdOrderByAmountDesc(user.id).map { it.toModel() }
    }

    fun findAllOrderByAscending(user: User): List<Income> {
        return incomeRepository.findAllByUserIdOrderByAmountAsc(user.id).map { it.toModel() }
    }
}
