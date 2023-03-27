package com.msum.finance.api.event

import com.msum.finance.api.repository.AccountRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toEntity
import com.msum.finance.user.repository.UserRepository
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class NetWorthService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val logger: Logger
) {
    fun calculateNetWorth(user: User) {
        val accounts = user.accounts
        val calculatedBalance = accounts.mapNotNull { account ->
            val startingBalance = account?.startingBalance ?: BigDecimal.ZERO
            val expenseTotal = account?.expenses?.sumOf { it.amount } ?: BigDecimal.ZERO
            val incomeTotal = account?.incomes?.sumOf { it.amount } ?: BigDecimal.ZERO
            startingBalance + expenseTotal + incomeTotal
        }.fold(BigDecimal.ZERO) { acc, value -> acc.add(value) }

        for (account in accounts) {
            val startingBalance = account?.startingBalance ?: BigDecimal.ZERO
            val expenseTotal = account?.expenses?.sumOf { it.amount } ?: BigDecimal.ZERO
            val incomeTotal = account?.incomes?.sumOf { it.amount } ?: BigDecimal.ZERO
            val totalBalance = startingBalance + expenseTotal + incomeTotal
            account?.balance = totalBalance
        }

        accountRepository.saveAll(accounts)
        userRepository.save(user.apply { netWorth = calculatedBalance }.toEntity())
        logger.info("Net worth and account balances calculated for user with ID: ${user.id}")
    }
}
