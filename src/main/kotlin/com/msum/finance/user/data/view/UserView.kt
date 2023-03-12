package com.msum.finance.user.data.view

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.msum.finance.api.data.view.AccountView
import com.msum.finance.user.data.Role
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserView(
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val email: String,
    val accounts: List<AccountView?>,
    val netWorth: BigDecimal,
    val createdAt: Instant,
    val updatedAt: Instant,
    val role: Enum<Role>
)
