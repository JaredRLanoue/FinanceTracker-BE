package com.msum.finance.common.data.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.http.HttpStatus

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ErrorResponse(
    val errorStatus: HttpStatus,
    val errorCode: Int,
    val errorMessage: String
)
