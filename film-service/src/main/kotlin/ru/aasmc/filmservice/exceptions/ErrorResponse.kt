package ru.aasmc.filmservice.exceptions

data class ErrorResponse(
    val message: String,
    val code: Int,
    val fieldErrors: List<String> = emptyList()
)
