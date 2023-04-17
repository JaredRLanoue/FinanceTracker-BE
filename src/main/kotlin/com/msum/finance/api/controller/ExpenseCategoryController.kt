package com.msum.finance.api.controller

import com.msum.finance.api.data.model.CategoriesView
import com.msum.finance.api.data.model.toView
import com.msum.finance.api.data.request.CategoryRequest
import com.msum.finance.api.data.view.CategoryView
import com.msum.finance.api.data.view.CategoryViewList
import com.msum.finance.api.service.ExpenseCategoryService
import com.msum.finance.user.data.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth/expense/categories")
class ExpenseCategoryController(
    @Autowired private val expenseCategoryService: ExpenseCategoryService
) {
    @PostMapping("/create")
    fun create(@AuthenticationPrincipal user: User, @RequestBody request: CategoryRequest) {
        return expenseCategoryService.create(user, request)
    }

    @GetMapping
    fun findAll(@AuthenticationPrincipal user: User): CategoryViewList {
        return CategoryViewList(expenseCategoryService.findAll(user).map { it.toView() }) // needs view object
    }

    @GetMapping("/{categoryId}")
    fun findById(@AuthenticationPrincipal user: User, @PathVariable categoryId: UUID): CategoryView? {
        return expenseCategoryService.findById(user, categoryId)?.toView()
    }

    @DeleteMapping("/delete/{categoryId}")
    fun deleteById(@AuthenticationPrincipal user: User, @PathVariable categoryId: UUID) {
        return expenseCategoryService.deleteById(user, categoryId) // ensure user exists in service?
    }

    @PutMapping("/update/{categoryId}")
    fun update(@AuthenticationPrincipal user: User, @RequestBody request: CategoryRequest, @PathVariable categoryId: UUID) {
        return expenseCategoryService.update(user, request, categoryId)
    }

    @GetMapping("/budgets")
    fun findCategoryTotals(@AuthenticationPrincipal user: User, @RequestParam(defaultValue = "all") sortMethod: String): CategoriesView {
        return expenseCategoryService.calculateTotals(user, sortMethod)
    }
}
