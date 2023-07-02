package ru.aasmc.filmservice.dto

data class FilmLikeDto(
    val timestamp: Long,
    val userId: Long,
    val operation: EventOperation,
    val filmId: Long,
    val mark: Int? = null,
)


enum class EventOperation {
    ADD,
    REMOVE
}

data class DeleteAllLikesDto(
    val filmId: Long,
    val timestamp: Long
)