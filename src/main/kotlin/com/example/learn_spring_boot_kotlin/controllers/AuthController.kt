package com.example.learn_spring_boot_kotlin.controllers

import com.example.learn_spring_boot_kotlin.dto.WebResponse
import com.example.learn_spring_boot_kotlin.service.AuthService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    data class AuthRequest(
        @field:Email(message = "Invalid email format.")
        val email: String,
        @field:Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}\$",
            message = "Password must be at least 9 characters long and contain at least one digit, uppercase and lowercase character."
        )
        val password: String
    )

    data class RefreshRequest(
        val refreshToken: String
    )

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: AuthRequest
    ) : ResponseEntity<WebResponse<Any>> {
        authService.register(body.email, body.password)

        val response = WebResponse<Any>(
            status = "success",
            message = "register success"
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: AuthRequest
    ): ResponseEntity<WebResponse<AuthService.TokenPair>> {
        val dataToken = authService.login(body.email, body.password)

        val response = WebResponse(
            status = "success",
            message = "login success",
            data = dataToken
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody body: RefreshRequest
    ): AuthService.TokenPair {
        return authService.refresh(body.refreshToken)
    }
}