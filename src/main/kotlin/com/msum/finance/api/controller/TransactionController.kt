package com.msum.finance.api.controller

import com.msum.finance.api.data.model.TransactionChartView
import com.msum.finance.api.data.model.TransactionList
import com.msum.finance.api.data.request.TransactionRequest
import com.msum.finance.api.service.TransactionService
import com.msum.finance.user.data.model.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {
    @GetMapping
    fun getAll(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false, defaultValue = "newest") sortMethod: String,
        @RequestParam(required = false) type: String?
    ): TransactionList {
        return transactionService.findAllTransactionsByQuery(sortMethod, type, user)
    }

    @DeleteMapping("delete/{type}/{transactionId}")
    fun delete(
        @AuthenticationPrincipal user: User,
        @PathVariable type: String,
        @PathVariable transactionId: UUID
    ) {
        return transactionService.delete(user, type, transactionId)
    }

    @PostMapping("/create")
    fun create(@AuthenticationPrincipal user: User, @RequestBody request: TransactionRequest) {
        println(request.date)
        return transactionService.create(user, request)
    }

    @PutMapping("/update/{transactionId}")
    fun update(
        @AuthenticationPrincipal user: User,
        @RequestBody request: TransactionRequest,
        @PathVariable transactionId: UUID
    ) {
        println(request.date)
        return transactionService.update(user, request, transactionId)
    }

    @GetMapping("/chart")
    fun getChart(@AuthenticationPrincipal user: User): TransactionChartView {
        return TransactionChartView(transactionService.calculateCharts(user))
    }
}
