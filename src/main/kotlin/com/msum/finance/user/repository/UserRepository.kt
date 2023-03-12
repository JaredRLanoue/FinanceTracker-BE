package com.msum.finance.user.repository

import com.msum.finance.user.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID> {
    fun findByLoginEmail(loginEmail: String?): UserEntity?

    fun existsByLoginEmail(loginEmail: String): Boolean
}
