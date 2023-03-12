package com.msum.finance.api.service

import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.Account
import com.msum.finance.api.data.model.toEntity
import com.msum.finance.api.data.request.AccountRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.event.NetWorthEvent
import com.msum.finance.api.repository.AccountRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val userService: UserService,
    @Autowired private val eventPublisher: ApplicationEventPublisher
) {
    fun create(user: User, request: AccountRequest) {
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")

        accountRepository.save(request.toModel(userData).toEntity())
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    fun findAll(user: User): List<Account> {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return accountRepository.findAllByUserId(user.id).map { it.toModel() }
    }

    fun findById(user: User, accountId: UUID): Account? {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return accountRepository.findByUserIdAndId(user.id, accountId)?.toModel()
    }

    fun deleteById(user: User, accountId: UUID) {
        accountRepository.deleteByUserIdAndId(user.id, accountId)
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    fun update(user: User, request: AccountRequest, accountId: UUID) {
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")
        val accountData = accountRepository.findByUserIdAndId(user.id, accountId)?.toModel() ?: throw Exception("Account doesn't exist")

        eventPublisher.publishEvent(NetWorthEvent(user))
        accountRepository.save(request.toModel(userData).apply { id = accountData.id }.toEntity())
    }
}
