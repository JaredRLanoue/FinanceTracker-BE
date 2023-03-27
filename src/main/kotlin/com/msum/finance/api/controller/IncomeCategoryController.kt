package com.msum.finance.api.controller

import com.msum.finance.api.data.model.toView
import com.msum.finance.api.data.request.CategoryRequest
import com.msum.finance.api.data.view.CategoryView
import com.msum.finance.api.service.IncomeCategoryService
import com.msum.finance.user.data.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth/income/categories")
class IncomeCategoryController(
    @Autowired private val incomeCategoryService: IncomeCategoryService
) {
    @PostMapping("/create")
    fun create(@AuthenticationPrincipal user: User, @RequestBody request: CategoryRequest) {
        return incomeCategoryService.create(user, request)
    }

    @GetMapping
    fun findAll(@AuthenticationPrincipal user: User): List<CategoryView> {
        return incomeCategoryService.findAll(user).map { it.toView() }
    }

    @GetMapping("/{categoryId}")
    fun findById(@AuthenticationPrincipal user: User, @PathVariable categoryId: UUID): CategoryView? {
        return incomeCategoryService.findById(user, categoryId)?.toView()
    }

//    @DeleteMapping("/delete/{accountId}")
//    fun deleteById(@AuthenticationPrincipal user: User, @PathVariable accountId: UUID) {
//        return categoryService.deleteById(user, accountId)
//    }

//    @PutMapping("/update/{accountId}")
//    fun update(@AuthenticationPrincipal user: User, @RequestBody request: IncomeRequest, @PathVariable accountId: UUID) {
//        return categoryService.update(user, request, accountId)
//    }
}
