package com.msum.finance.plaid.controller

import com.msum.finance.plaid.data.model.PlaidTransaction
import com.msum.finance.plaid.service.PlaidService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth/demo-transaction")
class PlaidController(@Autowired private val service: PlaidService) {
    @GetMapping
    fun getTransactionData(): List<PlaidTransaction>? {
        return service.getData()
    }
}
