package com.msum.finance.user.service

import com.msum.finance.api.repository.*
import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.entity.toModel
import com.msum.finance.user.data.model.User
import com.msum.finance.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val repository: UserRepository
) {
    fun getByUsername(username: String): User {
        return repository.findByLoginEmail(username)?.toModel() ?: throw Exception("Email not found")
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

//    fun calculateNetWorth(user: User) {
//        val accounts = accountRepository.findAllByUserId(user.id)
//        val calculatedNetWorth = accounts.sumOf { account ->
//            account.startingBalance + account.expenses.sumOf { it.amount } + account.incomes.sumOf { it.amount }
//        }
//        repository.save(user.apply { netWorth = calculatedNetWorth }.toEntity())
//    }
}
