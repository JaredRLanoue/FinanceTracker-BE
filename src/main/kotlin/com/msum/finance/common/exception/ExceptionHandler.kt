package com.msum.finance.common.exception

import com.msum.finance.common.data.response.ErrorResponse
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler(@Autowired private val logger: Logger) {

    @ExceptionHandler(Throwable::class)
    fun handleError(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("An error occurred: ${e.message}")
        val response = ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), e.message.toString())
        return ResponseEntity.badRequest().body(response)
    }
}
