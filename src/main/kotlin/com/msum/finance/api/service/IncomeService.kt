package com.msum.finance.api.service

import com.msum.finance.api.data.entity.toModel
import com.msum.finance.api.data.model.Income
import com.msum.finance.api.data.model.toEntity
import com.msum.finance.api.data.request.IncomeRequest
import com.msum.finance.api.data.request.toModel
import com.msum.finance.api.event.NetWorthEvent
import com.msum.finance.api.repository.IncomeRepository
import com.msum.finance.user.data.model.User
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class IncomeService(
    @Autowired private val incomeRepository: IncomeRepository,
    @Autowired private val userService: UserService,
    @Autowired private val eventPublisher: ApplicationEventPublisher
) {
    fun create(user: User, request: IncomeRequest) {
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist")

        incomeRepository.save(request.toModel(userData).toEntity())
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    fun findAll(user: User): List<Income> {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return incomeRepository.findAllByUserId(user.id).map { it.toModel() }
    }

    fun findById(user: User, accountId: UUID): Income? {
        eventPublisher.publishEvent(NetWorthEvent(user))
        return incomeRepository.findByUserIdAndId(user.id, accountId)?.toModel()
    }

    fun deleteById(user: User, incomeId: UUID) {
        incomeRepository.deleteByUserIdAndId(user.id, incomeId)
        eventPublisher.publishEvent(NetWorthEvent(user))
    }

    // Create an update request and change incomeRequest to incomeCreateRequest?
    fun update(user: User, request: IncomeRequest, incomeId: UUID) {
        val userData = userService.getByUserEmail(user.loginEmail) ?: throw Exception("User doesn't exist!")
        val incomeData = incomeRepository.findByUserIdAndId(user.id, incomeId)?.toModel() ?: throw Exception("Account doesn't exist")

        eventPublisher.publishEvent(NetWorthEvent(user))
        incomeRepository.save(request.toModel(userData).apply { id = incomeData.id }.toEntity())
    }
}
