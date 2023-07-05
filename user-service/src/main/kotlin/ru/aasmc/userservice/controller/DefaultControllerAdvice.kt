package ru.aasmc.userservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.aasmc.userservice.error.ErrorResponse
import ru.aasmc.userservice.error.UserServiceException

private val log = LoggerFactory.getLogger(DefaultControllerAdvice::class.java)

@RestControllerAdvice
class DefaultControllerAdvice {

    @ExceptionHandler(UserServiceException::class)
    fun handleUserServiceException(ex: UserServiceException): ResponseEntity<ErrorResponse> {
        log.error("Handling UserServiceException. Message: ${ex.message}. Code: ${ex.code}")
        val response = ErrorResponse(ex.message.orEmpty(), ex.code)
        return ResponseEntity(response, HttpStatus.valueOf(ex.code))
    }

}