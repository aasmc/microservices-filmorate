package ru.aasmc.filmservice.exceptions

import org.springframework.http.HttpStatus

class ResourceNotFoundException(
    val code: Int = HttpStatus.NOT_FOUND.value(),
    override val message: String
): RuntimeException(message)