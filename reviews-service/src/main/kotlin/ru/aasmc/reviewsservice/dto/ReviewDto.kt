package ru.aasmc.reviewsservice.dto

data class ReviewDto(
        val id: Long? = null,
        val content: String,
        val isPositive: Boolean,
        val userId: Long,
        val filmId: Long,
        val useful: Int? = 0
)
