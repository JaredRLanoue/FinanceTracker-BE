package com.msum.finance.user.service

import com.msum.finance.api.service.ExpenseCategoryService
import com.msum.finance.api.service.IncomeCategoryService
import com.msum.finance.user.data.entity.toModel
import com.msum.finance.user.data.request.AuthenticationRequest
import com.msum.finance.user.data.request.RegisterRequest
import com.msum.finance.user.data.request.toUser
import com.msum.finance.user.data.response.AuthenticationResponse
import com.msum.finance.user.repository.UserRepository
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    @Autowired private val repository: UserRepository,
    @Autowired private val expenseCategoryService: ExpenseCategoryService,
    @Autowired private val incomeCategoryService: IncomeCategoryService,
    @Autowired private val encoder: PasswordEncoder,
    @Autowired private val jwtService: JwtService,
    @Autowired private val userService: UserService,
    @Autowired private val authenticationManager: AuthenticationManager,
    @Autowired private val logger: Logger
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        if (repository.existsByLoginEmail(request.email)) {
            throw Exception("User already exists")
        }

        val newUser = request.apply { password = encoder.encode(password) }.toUser()
        val savedUser = repository.save(newUser).toModel()

        expenseCategoryService.saveDefaults(savedUser)
        incomeCategoryService.saveDefaults(savedUser)

        logger.info("User created with ID: ${newUser.id}")

        val jwtToken = jwtService.generateToken(savedUser)
        return AuthenticationResponse(token = jwtToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email.lowercase(),
                request.password
            )
        )
        val user = userService.getByUsername(request.email)
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(token = jwtToken)
    }
}
