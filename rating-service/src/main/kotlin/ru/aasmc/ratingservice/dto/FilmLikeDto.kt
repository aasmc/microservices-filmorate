package ru.aasmc.ratingservice.dto

data class FilmLikeDto(
        val timestamp: Long,
        val userId: Long,
        val operation: EventOperation,
        val filmId: Long,
        val mark: Int,
)


enum class EventOperation {
    ADD,
    REMOVE
}

data class DeleteAllLikesDto(
        val filmId: Long,
        val timestamp: Long
)