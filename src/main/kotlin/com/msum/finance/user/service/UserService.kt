package com.msum.finance.user.service

import com.msum.finance.user.data.entity.UserDetails
import com.msum.finance.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val repository: UserRepository) {
    fun findByUsername(username: String): UserDetails {
        return repository.findByLoginEmail(username) ?: throw Exception("Email not found")
    }
}
