package com.msum.finance.api.data.entity

import com.msum.finance.api.data.model.Account
import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.entity.toModel
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.Instant
import java.util.*

// TODO: Name has to be unique
@Entity
@Table(name = "accounts")
class AccountEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
    val name: String,
    val type: String,
    var balance: BigDecimal,
    val startingBalance: BigDecimal,
    @OneToMany(mappedBy = "accountId", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val expenses: List<ExpenseEntity> = emptyList(),
    @OneToMany(mappedBy = "accountId", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val incomes: List<IncomeEntity> = emptyList(),
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now()
)

fun AccountEntity.toModel() =
    Account(
        id = id,
        user = user.toModel(),
        expenses = expenses.map { it.toModel() },
        incomes = incomes.map { it.toModel() },
        name = name,
        type = type,
        balance = balance,
        startingBalance = startingBalance,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
