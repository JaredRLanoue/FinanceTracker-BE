package com.msum.finance.user.service

import com.msum.finance.user.data.request.AuthenticationRequest
import com.msum.finance.user.data.request.RegisterRequest
import com.msum.finance.user.data.request.createUser
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
    @Autowired private val encoder: PasswordEncoder,
    @Autowired private val jwtService: JwtService,
    @Autowired private val authenticationManager: AuthenticationManager,
    @Autowired private val logger: Logger
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        if (repository.existsByLoginEmail(request.email)) {
            throw Exception()
        }

        val newUser = request.createUser(encoder)
        repository.save(newUser)
        logger.info("User Created: ${newUser.loginEmail}")
        val jwtToken = jwtService.generateToken(newUser)
        return AuthenticationResponse(token = jwtToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val user = repository.findByLoginEmail(request.email) ?: throw Exception("Email not found")
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(token = jwtToken)
    }
}
