package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.ExpenseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

@Repository
interface ExpenseRepository : JpaRepository<ExpenseEntity, UUID> {
    @Query("SELECT e FROM ExpenseEntity e WHERE e.account.user.id = :userId AND e.date BETWEEN :startDate AND :endDate")
    fun findExpensesByDateRange(userId: UUID, startDate: Instant, endDate: Instant): List<ExpenseEntity>
}
