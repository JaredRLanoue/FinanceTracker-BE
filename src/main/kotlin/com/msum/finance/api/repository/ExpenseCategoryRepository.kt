package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.ExpenseCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ExpenseCategoryRepository : JpaRepository<ExpenseCategoryEntity, UUID> {
    // get using name and accountId, then you can get all transactions in account with name and accountId or even within a date range
    // then use this to collect monthly expenses in categories and create budgets
    // so to get category view, you can getAllCategories which would return totals for each category within a date range and the total
    // of the accounts expenses to get the percentage for each category

    fun findAllByUserId(userId: UUID): List<ExpenseCategoryEntity>

    fun findByUserIdAndId(userId: UUID, expenseId: UUID): ExpenseCategoryEntity?

    fun findByUserIdAndName(userId: UUID, name: String): ExpenseCategoryEntity?
}
