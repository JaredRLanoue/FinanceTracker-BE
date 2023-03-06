package com.msum.finance.user.controller

import com.msum.finance.user.data.entity.toModel
import com.msum.finance.user.data.model.toView
import com.msum.finance.user.data.view.UserView
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth/users")
class UserController(@Autowired private val userService: UserService) {
    @GetMapping
    fun findAll(): List<UserView> {
        return userService.findAll().map { it.toModel().toView() }
    }

    @GetMapping("/create")
    fun createDemoUser() {
        return userService.createDummyData()
    }
}
