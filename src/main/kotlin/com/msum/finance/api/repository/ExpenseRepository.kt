package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.ExpenseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

// TODO: Add pageable for accounts, expenses, and incomes. This will be for the front-end table views. 20 per page default, with option to change on frontend?
@Repository
interface ExpenseRepository : PagingAndSortingRepository<ExpenseEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<ExpenseEntity>

    fun findAllByUserId(sort: Sort, userId: UUID): List<ExpenseEntity>

    fun findByUserIdAndId(userId: UUID, expenseId: UUID): ExpenseEntity?

    @Modifying
    @Query("DELETE FROM ExpenseEntity e WHERE e.id = :id")
    fun deleteById(@Param("id") id: UUID): Int

    fun save(expense: ExpenseEntity): ExpenseEntity

    fun findByAccountId(accountId: UUID, pageable: Pageable): Page<ExpenseEntity>

    @Query("SELECT e FROM ExpenseEntity e WHERE e.user.id = :userId AND e.date >= :startDate AND e.date <= :endDate")
    fun findAllExpensesByUserIdAndDateRange(userId: UUID, startDate: Instant, endDate: Instant): List<ExpenseEntity>
}
