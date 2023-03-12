package com.msum.finance.api.controller

import com.msum.finance.api.data.model.toView
import com.msum.finance.api.data.request.AccountRequest
import com.msum.finance.api.data.view.AccountView
import com.msum.finance.api.service.AccountService
import com.msum.finance.user.data.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth/accounts")
class AccountController(
    @Autowired private val accountService: AccountService
) {
    @PostMapping("/create")
    fun create(@AuthenticationPrincipal user: User, @RequestBody request: AccountRequest) {
        return accountService.create(user, request)
    }

    @GetMapping
    fun findAll(@AuthenticationPrincipal user: User): List<AccountView> {
        return accountService.findAll(user).map { it.toView() }
    }

    @GetMapping("/{accountId}")
    fun findById(@AuthenticationPrincipal user: User, @PathVariable accountId: UUID): AccountView? {
        return accountService.findById(user, accountId)?.toView()
    }

    @DeleteMapping("/delete/{accountId}")
    fun deleteById(@AuthenticationPrincipal user: User, @PathVariable accountId: UUID) {
        return accountService.deleteById(user, accountId)
    }

    @PutMapping("/update/{accountId}")
    fun update(@AuthenticationPrincipal user: User, @RequestBody request: AccountRequest, @PathVariable accountId: UUID) {
        return accountService.update(user, request, accountId)
    }
}
