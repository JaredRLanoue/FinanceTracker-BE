package com.msum.finance.api.data.entity

import com.msum.finance.api.data.model.Category
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "categories")
class CategoryEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now()
)

fun CategoryEntity.toModel() =
    Category(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
