package com.msum.finance.api.data.entity

import com.msum.finance.api.data.model.Location
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "locations")
class LocationEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    @ManyToMany
    @JoinColumn(name = "expense_id")
    val expenses: List<ExpenseEntity> = mutableListOf(),
    val address: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now()
)

fun LocationEntity.toModel() = Location(
    id = id,
    address = address,
    city = city,
    state = state,
    country = country,
    postalCode = postalCode,
    createdAt = createdAt,
    updatedAt = updatedAt
)
