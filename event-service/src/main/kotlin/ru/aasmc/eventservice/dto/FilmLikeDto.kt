package ru.aasmc.eventservice.dto

import ru.aasmc.eventservice.enums.CommonEventOperation

data class FilmLikeDto(
        val timestamp: Long,
        val userId: Long,
        val operation: CommonEventOperation,
        val filmId: Long,
        val mark: Int? = null,
)
