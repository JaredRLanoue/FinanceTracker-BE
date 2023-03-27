package com.msum.finance.api.controller

import com.msum.finance.api.data.model.CategoriesTotal
import com.msum.finance.api.data.model.toView
import com.msum.finance.api.data.request.IncomeRequest
import com.msum.finance.api.data.view.IncomeView
import com.msum.finance.api.service.IncomeService
import com.msum.finance.user.data.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth/incomes")
class IncomeController(
    @Autowired private val incomeService: IncomeService
) {
    @PostMapping("/create")
    fun create(@AuthenticationPrincipal user: User, @RequestBody request: IncomeRequest) {
        return incomeService.create(user, request)
    }

    @GetMapping
    fun findAll(@AuthenticationPrincipal user: User): List<IncomeView> {
        return incomeService.findAll(user).map { it.toView() }
    }

    @GetMapping("/{accountId}")
    fun findById(@AuthenticationPrincipal user: User, @PathVariable accountId: UUID): IncomeView? {
        return incomeService.findById(user, accountId)?.toView()
    }

    @DeleteMapping("/delete/{accountId}")
    fun deleteById(@AuthenticationPrincipal user: User, @PathVariable accountId: UUID) {
        return incomeService.deleteById(user, accountId)
    }

    @PutMapping("/update/{accountId}")
    fun update(@AuthenticationPrincipal user: User, @RequestBody request: IncomeRequest, @PathVariable accountId: UUID) {
        return incomeService.update(user, request, accountId)
    }

    // separate into IncomeCategoryController or keep here?
    @GetMapping("/categories")
    fun findCategoryTotals(@AuthenticationPrincipal user: User): CategoriesTotal {
        return incomeService.findAllCategoryTotals(user)
    }
}
