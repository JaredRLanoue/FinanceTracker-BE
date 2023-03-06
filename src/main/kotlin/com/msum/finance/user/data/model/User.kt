package com.msum.finance.user.data.model

import com.msum.finance.api.data.entity.AccountEntity
import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.toView
import com.msum.finance.user.data.Role
import com.msum.finance.user.data.view.UserView
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant
import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val loginEmail: String,
    val loginPassword: String,
    var accounts: List<AccountEntity?>,
    val createdAt: Instant,
    val updatedAt: Instant,
    val role: List<SimpleGrantedAuthority>
) : UserDetails {
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return role
    }

    override fun getPassword(): String {
        return loginPassword
    }

    override fun getUsername(): String {
        return loginEmail
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

fun User.toView() = UserView(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = loginEmail,
    role = Role.USER,
    accounts = accounts.map { it?.toModel()?.toView() },
    updatedAt = updatedAt,
    createdAt = createdAt
)
