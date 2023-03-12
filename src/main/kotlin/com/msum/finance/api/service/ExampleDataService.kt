package com.msum.finance.api.service

import com.msum.finance.api.data.entity.*
import com.msum.finance.api.repository.*
import com.msum.finance.user.data.Role
import com.msum.finance.user.data.entity.UserEntity
import com.msum.finance.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class ExampleDataService(
    @Autowired private val repository: UserRepository,
    @Autowired private val locationRepository: LocationRepository,
    @Autowired private val categoryRepository: CategoryRepository,
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val incomeRepository: IncomeRepository
) {
    fun createExampleData() {
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
                startingBalance = BigDecimal(10000),
                expenses = listOf(),
                incomes = listOf()
            )
        )

        val expense = expenseRepository.save(
            ExpenseEntity(
                accountId = account.id,
                category = category.name,
//                locationId = location.id,
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
                accountId = account.id,
                user = user,
                amount = BigDecimal(100),
                date = Instant.now(),
                payerName = "Bushel",
                description = "Biweekly income",
                category = "Payroll"
            )
        )
    }
}
