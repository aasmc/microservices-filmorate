package ru.aasmc.filmservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.aasmc.filmservice.exceptions.BusinessServiceException
import ru.aasmc.filmservice.exceptions.ErrorResponse
import ru.aasmc.filmservice.exceptions.ResourceNotFoundException

private val log = LoggerFactory.getLogger(DefaultControllerAdvice::class.java)

@RestControllerAdvice
class DefaultControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        log.error("Received ResourceNotFoundException with message: {}", ex.message)
        val response = ErrorResponse(code = ex.code, message = ex.message)
        return ResponseEntity(response, HttpStatus.valueOf(ex.code))
    }

    @ExceptionHandler(BusinessServiceException::class)
    fun handleBusinessError(ex: BusinessServiceException): ResponseEntity<ErrorResponse> {
        log.error("Received BusinessServiceException with message: {}", ex.message)
        val response = ErrorResponse(code = ex.code, message = ex.message)
        return ResponseEntity(response, HttpStatus.valueOf(ex.code))
    }

}