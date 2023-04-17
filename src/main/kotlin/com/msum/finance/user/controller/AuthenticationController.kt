package com.msum.finance.user.controller

import com.msum.finance.user.data.request.AuthenticationRequest
import com.msum.finance.user.data.request.JwtRequest
import com.msum.finance.user.data.request.RegisterRequest
import com.msum.finance.user.data.response.AuthenticationResponse
import com.msum.finance.user.service.AuthenticationService
import com.msum.finance.user.service.JwtService
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    @Autowired private val service: AuthenticationService,
    @Autowired private val jwtService: JwtService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.register(request))
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.authenticate(request))
    }

    @GetMapping("/decode-jwt")
    fun decodeJwt(@RequestBody request: JwtRequest): Claims {
        return jwtService.extractAllClaims(request.jwt)
    }
}
