package com.msum.finance.user.controller

import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/demo-controller")
class DemoController(@Autowired private val userService: UserService) {
    @GetMapping
    fun findAll(): ResponseEntity<String> {
        return ResponseEntity.ok().body("Hello from auth endpoint!")
    }
}
