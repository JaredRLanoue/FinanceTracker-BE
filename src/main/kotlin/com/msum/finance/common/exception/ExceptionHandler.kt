package com.msum.finance.common.exception

import com.msum.finance.common.data.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(Exception::class, java.lang.Exception::class)
    fun handleUserError(e: Exception): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), e.message.toString())
        return ResponseEntity.badRequest().body(response)
    }
}
