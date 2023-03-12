package com.msum.finance.api.data.entity

import com.msum.finance.api.data.model.ExpenseCategory
import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.entity.toModel
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "expenses_categories")
class ExpenseCategoryEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,
    val name: String,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now()
)

fun ExpenseCategoryEntity.toModel() =
    ExpenseCategory(
        id = id,
        user = user.toModel(),
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
