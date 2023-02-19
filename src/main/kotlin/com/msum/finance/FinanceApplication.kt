package com.msum.finance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinanceApplication

fun main(args: Array<String>) {
    runApplication<FinanceApplication>(*args)
}
