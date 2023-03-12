package com.msum.finance.api.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NetWorthListener(@Autowired private val service: NetWorthService) {
    @EventListener
    fun onNetWorthCalculation(event: NetWorthEvent) {
        service.calculateNetWorth(event.user)
    }
}
