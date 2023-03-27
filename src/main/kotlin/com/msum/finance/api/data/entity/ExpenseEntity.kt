package com.msum.finance.api.data.entity

import com.msum.finance.api.data.model.Expense
import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.entity.toModel
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "expenses")
class ExpenseEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    @JoinColumn(name = "account_id")
    val accountId: UUID,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,
    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: ExpenseCategoryEntity,
    val amount: BigDecimal,
    val description: String,
    val merchantName: String,
    val pending: Boolean,
    val date: Instant,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now()
)

fun ExpenseEntity.toModel() = Expense(
    id = id,
    accountId = accountId,
    user = user.toModel(),
    category = category.toModel(),
    amount = amount,
    description = description,
    merchantName = merchantName,
    pending = pending,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt
)
