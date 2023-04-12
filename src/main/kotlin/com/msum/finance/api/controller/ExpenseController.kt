package com.msum.finance.api.controller

import com.msum.finance.api.data.model.CategoriesView
import com.msum.finance.api.data.model.toView
import com.msum.finance.api.data.request.ExpenseRequest
import com.msum.finance.api.data.view.ExpenseView
import com.msum.finance.api.service.ExpenseService
import com.msum.finance.user.data.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth/expenses")
class ExpenseController(
    @Autowired private val expenseService: ExpenseService
) {
    @PostMapping("/create")
    fun create(@AuthenticationPrincipal user: User, @RequestBody request: ExpenseRequest) {
        return expenseService.create(user, request)
    }

    @GetMapping
    fun findAll(@AuthenticationPrincipal user: User): List<ExpenseView> {
        return expenseService.findAll(user).map { it.toView() }
    }

    @GetMapping("/{expenseId}")
    fun findById(@AuthenticationPrincipal user: User, @PathVariable expenseId: UUID): ExpenseView? {
        return expenseService.findById(user, expenseId)?.toView()
    }

    @DeleteMapping("/delete/{expenseId}")
    fun deleteById(@AuthenticationPrincipal user: User, @PathVariable expenseId: UUID) {
        return expenseService.deleteById(user, expenseId)
    }

    @PutMapping("/update/{expenseId}")
    fun update(@AuthenticationPrincipal user: User, @RequestBody request: ExpenseRequest, @PathVariable expenseId: UUID) {
        return expenseService.update(user, request, expenseId)
    }

    @GetMapping("/categories")
    fun findCategoryTotals(@AuthenticationPrincipal user: User, @RequestParam(defaultValue = "all") sortMethod: String): CategoriesView {
        return expenseService.findAllCategoryTotals(user, sortMethod)
    }
}
