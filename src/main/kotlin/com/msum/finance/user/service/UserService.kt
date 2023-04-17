package com.msum.finance.user.service

import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.entity.toModel
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import com.msum.finance.user.data.request.UpdateRequest
import com.msum.finance.user.data.request.toUser
import com.msum.finance.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    @Autowired private val repository: UserRepository
) {
    fun getByUsername(username: String): User {
        return repository.findByLoginEmail(username)?.toModel() ?: throw Exception("Email not found") // update to correct describe what's happening, as this means user is not found
    }

    fun existsByUsername(email: String) {
        if (repository.existsByLoginEmail(email)) {
            throw Exception("Email already exists!")
        }
    }

    fun getByUserEmail(email: String): User? {
        return repository.findByLoginEmail(email)?.toModel()
    }

    fun findAll(): List<UserEntity> {
        return repository.findAll()
    }

    fun checkAccountExistsForUser(accountId: UUID, user: User) {
        if (accountId !in user.accounts.map { it?.id }) {
            throw Exception("Account doesn't exist for the user")
        }
    }

    fun delete(user: User) {
        repository.delete(user.toEntity())
    }

    fun update(user: User, request: UpdateRequest) {
        if (request.email == null && request.firstName == null && request.lastName == null) {
            return
        }
        repository.save(request.toUser(user).apply { id = user.id })
    }
}
