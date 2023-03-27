package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.ExpenseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

// TODO: Add pageable for accounts, expenses, and incomes. This will be for the front-end table views. 20 per page default, with option to change on frontend?
@Repository
interface ExpenseRepository : PagingAndSortingRepository<ExpenseEntity, UUID> {
    @Query("SELECT e FROM ExpenseEntity e WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate")
    fun findExpensesByDateRange(userId: UUID, startDate: Instant, endDate: Instant): List<ExpenseEntity>

    @Query("SELECT e FROM ExpenseEntity e WHERE e.user.id = :userId AND e.category.name = :category")
    fun findExpensesByCategory(userId: UUID, category: String): List<ExpenseEntity>

    fun findAllByUserIdOrderByAmountDesc(userId: UUID): List<ExpenseEntity>

    fun findAllByUserIdOrderByAmountAsc(userId: UUID): List<ExpenseEntity>

    fun findAllByUserId(userId: UUID): List<ExpenseEntity>

    fun findByUserIdAndId(userId: UUID, expenseId: UUID): ExpenseEntity?

    fun deleteById(userId: UUID)

    fun save(expense: ExpenseEntity): ExpenseEntity

    fun findByAccountId(accountId: UUID, pageable: Pageable): Page<ExpenseEntity>

    @Query("SELECT c.name AS category, SUM(e.amount) AS total FROM ExpenseEntity e JOIN e.category c WHERE e.user.id = :userId GROUP BY c.name, c.id")
    fun findCategoryTotalsByUser(userId: UUID): List<Map<String, Any>>

    // combine into one, or keep separate?
    @Query("SELECT c.name AS category, SUM(e.amount) AS total FROM ExpenseEntity e JOIN e.category c WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate GROUP BY c.name")
    fun findCategoryTotalsByUserAndDateRange(userId: UUID, startDate: Instant, endDate: Instant): List<Map<String, Any>>
}
