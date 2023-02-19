package com.msum.finance.user.service

import com.msum.finance.user.configuration.JwtService
import com.msum.finance.user.models.request.AuthenticationRequest
import com.msum.finance.user.models.request.RegisterRequest
import com.msum.finance.user.models.request.createUser
import com.msum.finance.user.models.view.AuthenticationResponse
import com.msum.finance.user.repository.UserRepository
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
    @Autowired private val authenticationManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        val newUser = request.createUser(request, encoder)
        repository.save(newUser)
        val jwtToken = jwtService.generateToken(newUser) ?: throw Exception()
        return AuthenticationResponse(token = jwtToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val user = repository.findByLoginEmail(request.email) ?: throw Exception()
        val jwtToken = jwtService.generateToken(user) ?: throw Exception()
        return AuthenticationResponse(token = jwtToken)
    }
}
