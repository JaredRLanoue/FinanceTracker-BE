package com.msum.finance.user.controller

import com.msum.finance.api.service.ExampleDataService
import com.msum.finance.user.data.entity.toModel
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toView
import com.msum.finance.user.data.view.UserView
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/auth/user")
class UserController(@Autowired private val userService: UserService, @Autowired private val exampleDataService: ExampleDataService) {
    @GetMapping("/find-all")
    fun findAll(): List<UserView> {
        return userService.findAll().map { it.toModel().toView() }
    }

    @GetMapping("/find")
    fun findByUser(@AuthenticationPrincipal user: User): UserView? {
        return userService.getByUserEmail(user.loginEmail)?.toView()
    }

    @PostMapping("/create-example-data/{userId}")
    fun createExampleData(@PathVariable userId: UUID) {
        return exampleDataService.createExampleData(userId)
    }
}
