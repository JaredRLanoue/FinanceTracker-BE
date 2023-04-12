package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.AccountEntity
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : PagingAndSortingRepository<AccountEntity, UUID> {
    fun findAllByUserId(sort: Sort, userId: UUID): List<AccountEntity>

    fun findAccountById(userId: UUID): AccountEntity?

    @Modifying
    @Query("DELETE FROM AccountEntity a WHERE a.id = :id")
    fun deleteAccountById(@Param("id") id: UUID): Int

    fun save(account: AccountEntity): AccountEntity
}
