package com.msum.finance.user.repository

import com.msum.finance.user.data.entity.UserDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserDetails, UUID> {
    fun findByLoginEmail(loginEmail: String): UserDetails?

    fun existsByLoginEmail(loginEmail: String): Boolean
}
