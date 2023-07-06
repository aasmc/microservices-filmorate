package ru.aasmc.reviewsservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.aasmc.reviewsservice.error.ErrorResponse
import ru.aasmc.reviewsservice.error.ReviewServiceException

private val log = LoggerFactory.getLogger(DefaultControllerAdvice::class.java)

@RestControllerAdvice
class DefaultControllerAdvice {

    @ExceptionHandler(ReviewServiceException::class)
    fun handleReviewServiceException(ex: ReviewServiceException): ResponseEntity<ErrorResponse> {
        log.error("Handling error: {}. Status code: {}", ex.code, ex.message)
        val response = ErrorResponse(
                code = ex.code,
                message = ex.message
        )
        return ResponseEntity(response, HttpStatus.valueOf(ex.code))
    }

}