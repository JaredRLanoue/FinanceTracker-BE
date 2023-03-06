package com.msum.finance.user.service

import com.msum.finance.api.data.entity.*
import com.msum.finance.api.repository.*
import com.msum.finance.user.data.Role
import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.entity.toModel
import com.msum.finance.user.data.model.User
import com.msum.finance.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class UserService(
    @Autowired private val repository: UserRepository,
    @Autowired private val locationRepository: LocationRepository,
    @Autowired private val categoryRepository: CategoryRepository,
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val incomeRepository: IncomeRepository
) {
    fun findByUsername(username: String): User {
        return repository.findByLoginEmail(username)?.toModel() ?: throw Exception("Email not found")
    }

    fun existsByUsername(email: String) {
        if (repository.existsByLoginEmail(email)) {
            throw Exception("Email already exists!")
        }
    }

    fun findAll(): List<UserEntity> {
        return repository.findAll()
    }

    fun createDummyData() {
        val user = repository.save(
            UserEntity(
                firstName = "Jared",
                lastName = "L",
                loginEmail = "jared@gmail.com",
                loginPassword = "password",
                role = Role.USER
            )
        )

        val location = locationRepository.save(
            LocationEntity(
                address = "2014 Forest Hills Drive",
                city = "Fayetteville",
                state = "NC",
                country = "US",
                postalCode = "28303"
            )
        )
        val category = categoryRepository.save(
            CategoryEntity(
                name = "Retail"
            )
        )

        val account = accountRepository.save(
            AccountEntity(
                user = user,
                name = "Chase Checking",
                type = "Checking",
                balance = BigDecimal(10000),
                expenses = listOf()
            )
        )

        val expense = expenseRepository.save(
            ExpenseEntity(
                account = account,
                category = category.name,
                location = location,
                amount = BigDecimal(250),
                description = "The Last of Us Collectors Edition",
                merchantName = "GameStop",
                pending = false,
                date = Instant.now(),
                user = user
            )
        )

        val income = incomeRepository.save(
            IncomeEntity(
                account = account,
                user = user,
                amount = BigDecimal(100),
                date = Instant.now(),
                name = "Biweekly Income"
            )
        )
    }
}
