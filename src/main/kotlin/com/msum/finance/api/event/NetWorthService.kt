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
    fun calculateNetWorth(user: User) { // need to change how this is done. this is bad.
        val accounts = user.accounts
        val calculatedBalance = accounts.mapNotNull { account ->
            val startingBalance = account?.startingBalance ?: BigDecimal.ZERO
            val expenseTotal = account?.expenses?.sumOf { it.amount } ?: BigDecimal.ZERO
            val incomeTotal = account?.incomes?.sumOf { it.amount } ?: BigDecimal.ZERO
            startingBalance + expenseTotal + incomeTotal
        }.fold(BigDecimal.ZERO) { total, value -> total.add(value) }

        accounts.forEach { account ->
            val startingBalance = account?.startingBalance ?: BigDecimal.ZERO
            val expenseTotal = account?.expenses?.sumOf { it.amount } ?: BigDecimal.ZERO
            val incomeTotal = account?.incomes?.sumOf { it.amount } ?: BigDecimal.ZERO
            val totalBalance = startingBalance + expenseTotal + incomeTotal

            if (account != null && account.balance != totalBalance) {
                account.balance = totalBalance
                accountRepository.save(account)
                logger.info("Account balances saved for user with ID: ${user.id}")
            }
        }

        if (calculatedBalance != user.netWorth) {
            userRepository.save(user.apply { netWorth = calculatedBalance }.toEntity())
            logger.info("Net worth saved for user with ID: ${user.id}")
        }
    }
}
