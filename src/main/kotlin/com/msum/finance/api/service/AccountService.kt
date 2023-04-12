package com.msum.finance.api.service

import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.*
import com.msum.finance.api.data.request.AccountRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.event.NetWorthEvent
import com.msum.finance.api.repository.AccountRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.service.UserService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.*
import java.util.*

@Service
@Transactional
class AccountService(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val userService: UserService,
    @Autowired private val eventPublisher: ApplicationEventPublisher
) {
    // remove the event from findById, findByAll, since you shouldn't be using database to add expenses. Might change back?
    fun create(user: User, request: AccountRequest) {
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")

        accountRepository.save(request.toModel(userData).toEntity())
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    fun findAll(sortMethod: String, user: User): AccountList {
        eventPublisher.publishEvent(NetWorthEvent(user))
        val sort: Sort = when (sortMethod) {
            "newest" -> Sort.by(Sort.Direction.DESC, "createdAt")
            "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt")
            "largest" -> Sort.by(Sort.Direction.DESC, "balance")
            "smallest" -> Sort.by(Sort.Direction.ASC, "balance")
            else -> Sort.by(Sort.Direction.DESC, "createdAt")
        }

        val accounts = accountRepository.findAllByUserId(sort, user.id).map { it.toModel() }
        val total = user.accounts.size
        val netWorth = user.netWorth
//        val netWorth = calculateNetWorth(user)
        val average = if (accounts.isNotEmpty()) {
            (netWorth / accounts.size.toBigDecimal().setScale(2))
        } else {
            BigDecimal.ZERO
        }

        val metaData = AccountMeta(total = total, average = average, netWorth = netWorth)
        return AccountList(data = accounts.map { it.toView() }, meta = metaData)
    }

    fun findById(user: User, accountId: UUID): Account? {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return accountRepository.findAccountById(accountId)?.toModel()
    }

    fun deleteById(user: User, accountId: UUID) {
        val account = findById(user, accountId)?.toEntity() ?: throw Exception("Account doesn't exist")
        accountRepository.deleteAccountById(account.id)
        if (findById(user, accountId) != null) {
            throw Exception("Account was not deleted")
        }
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    fun update(user: User, request: AccountRequest, accountId: UUID) {
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        val accountData = accountRepository.findAccountById(accountId)?.toModel() ?: throw Exception("Account doesn't exist")

        eventPublisher.publishEvent(NetWorthEvent(user))
        accountRepository.save(request.toModel(userData).apply { id = accountData.id }.toEntity())
    }

    fun calculateNetWorth(user: User): BigDecimal {
        val accounts = user.accounts
        val calculatedBalance = accounts.mapNotNull { account ->
            val startingBalance = account?.startingBalance ?: BigDecimal.ZERO
            val expenseTotal = account?.expenses?.sumOf { it.amount } ?: BigDecimal.ZERO
            val incomeTotal = account?.incomes?.sumOf { it.amount } ?: BigDecimal.ZERO
            startingBalance + expenseTotal + incomeTotal
        }.fold(BigDecimal.ZERO) { acc, value -> acc.add(value) }

        return calculatedBalance
    }
}
