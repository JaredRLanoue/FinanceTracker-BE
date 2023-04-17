package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.IncomeEntity
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

@Repository
interface IncomeRepository : PagingAndSortingRepository<IncomeEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<IncomeEntity>

    fun findAllByUserId(sort: Sort, userId: UUID): List<IncomeEntity>

    fun findByUserIdAndId(userId: UUID, incomeId: UUID): IncomeEntity?

    @Modifying
    @Query("DELETE FROM IncomeEntity i WHERE i.id = :id")
    fun deleteById(@Param("id") id: UUID): Int

    @Query("SELECT i FROM IncomeEntity i WHERE i.user.id = :userId AND i.date BETWEEN :startDate AND :endDate")
    fun findIncomesByDateRange(userId: UUID, startDate: Instant, endDate: Instant): List<IncomeEntity>

    @Query("SELECT c.name AS category, SUM(i.amount) AS total FROM IncomeEntity i JOIN i.category c WHERE i.user.id = :userId GROUP BY c.name, c.id")
    fun findCategoryTotalsByUser(userId: UUID): List<Map<String, Any>>

    fun findAllByUserIdOrderByAmountDesc(userId: UUID): List<IncomeEntity>

    fun findAllByUserIdOrderByAmountAsc(userId: UUID): List<IncomeEntity>

    fun save(income: IncomeEntity): IncomeEntity

    fun findByUserId(userId: UUID, name: String, pageable: Pageable): Page<IncomeEntity>
}
