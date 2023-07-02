package ru.aasmc.filmservice.exceptions

import org.springframework.http.HttpStatus

class ResourceAlreadyExistsException(
    val code: Int = HttpStatus.CONFLICT.value(),
    message: String
): RuntimeException(message)