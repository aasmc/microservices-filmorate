package ru.aasmc.filmservice.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.aasmc.filmservice.exceptions.BusinessServiceException
import ru.aasmc.filmservice.exceptions.ErrorResponse
import ru.aasmc.filmservice.exceptions.ResourceNotFoundException

private val log = LoggerFactory.getLogger(DefaultControllerAdvice::class.java)

@RestControllerAdvice
class DefaultControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ErrorResponse {
        log.error("Received ResourceNotFoundException with message: {}", ex.message)
        return ErrorResponse(code = ex.code, message = ex.message)
    }

    @ExceptionHandler(BusinessServiceException::class)
    fun handleBusinessError(ex: BusinessServiceException): ErrorResponse {
        log.error("Received BusinessServiceException with message: {}", ex.message)
        return ErrorResponse(code = ex.code, message = ex.message)
    }

}