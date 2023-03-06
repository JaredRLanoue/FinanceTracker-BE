package com.msum.finance.user.data.entity

import com.msum.finance.api.data.entity.AccountEntity
import com.msum.finance.user.data.Role
import com.msum.finance.user.data.model.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val loginEmail: String,
    val loginPassword: String,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val accounts: List<AccountEntity?> = listOf(),
    @Enumerated(EnumType.STRING)
    val role: Role,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
)

fun UserEntity.toModel() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    loginEmail = loginEmail,
    loginPassword = loginPassword,
    role = listOf(SimpleGrantedAuthority(role.name)),
    accounts = accounts,
    updatedAt = updatedAt,
    createdAt = createdAt
)
