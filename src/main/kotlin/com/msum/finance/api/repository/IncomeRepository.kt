package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.IncomeEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IncomeRepository : JpaRepository<IncomeEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<IncomeEntity>

    fun findByUserIdAndId(userId: UUID, incomeId: UUID): IncomeEntity?

    fun deleteByUserIdAndId(userId: UUID, id: UUID)
}
