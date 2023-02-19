package com.msum.finance.user.models.entity

import com.msum.finance.user.models.Role
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
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
    @Enumerated(EnumType.STRING)
    val role: Role
) : UserDetails {
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
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
