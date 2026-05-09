package com.example.learn_spring_boot_kotlin

import com.example.learn_spring_boot_kotlin.dto.WebResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationError(e: MethodArgumentNotValidException): ResponseEntity<WebResponse<Any>>{
        val errorMessages = e.bindingResult.allErrors.map {
            it.defaultMessage ?: "Invalid value"
        }

        val response = WebResponse<Any>(
            status = "error",
            message = "failed validation, check yout input.",
            errors = errorMessages
        )

        return ResponseEntity.status(400).body(response)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(e: BadCredentialsException) : ResponseEntity<WebResponse<Any>>{
        val response = WebResponse<Any>(
            status = "error",
            message = e.message ?: "Incorect email or password.",
        )

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(e: ResponseStatusException): ResponseEntity<WebResponse<Any>>{
        val response = WebResponse<Any>(
            status = "error",
            message = e.reason ?: "Something wrong."
        )

        return ResponseEntity.status(e.statusCode).body(response)
    }
}