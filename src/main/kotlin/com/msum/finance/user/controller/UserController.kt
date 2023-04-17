package com.msum.finance.user.controller

import com.msum.finance.api.service.ExampleDataService
import com.msum.finance.user.data.entity.toModel
import com.msum.finance.user.data.model.User
import com.msum.finance.user.data.model.toView
import com.msum.finance.user.data.request.UpdateRequest
import com.msum.finance.user.data.view.UserView
import com.msum.finance.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
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

    @PostMapping("/create-example-data")
    fun createExampleData(@AuthenticationPrincipal user: User) {
        return exampleDataService.createExampleData(user.id)
    }

    @DeleteMapping("/delete")
    fun delete(@AuthenticationPrincipal user: User) {
        return userService.delete(user)
    }

    @PutMapping("/update")
    fun update(@AuthenticationPrincipal user: User, @RequestBody request: UpdateRequest) {
        return userService.update(user, request)
    }
}
