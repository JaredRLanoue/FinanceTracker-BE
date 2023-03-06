package com.msum.finance.user.service

import com.msum.finance.api.data.entity.AccountEntity
import com.msum.finance.api.data.entity.CategoryEntity
import com.msum.finance.api.data.entity.ExpenseEntity
import com.msum.finance.api.data.entity.LocationEntity
import com.msum.finance.api.repository.AccountRepository
import com.msum.finance.api.repository.CategoryRepository
import com.msum.finance.api.repository.ExpenseRepository
import com.msum.finance.api.repository.LocationRepository
import com.msum.finance.user.data.Role
import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.data.entity.toModel
import com.msum.finance.user.data.model.User
import com.msum.finance.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Service
class UserService(
    @Autowired private val repository: UserRepository,
    @Autowired private val locationRepository: LocationRepository,
    @Autowired private val categoryRepository: CategoryRepository,
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val accountRepository: AccountRepository
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

        val transaction = expenseRepository.save(
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
    }
}
