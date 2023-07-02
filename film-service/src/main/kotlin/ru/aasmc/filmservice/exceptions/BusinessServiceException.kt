package ru.aasmc.filmservice.exceptions

class BusinessServiceException(val code: Int, override val message: String): RuntimeException(message) {
}