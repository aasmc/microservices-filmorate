package ru.aasmc.reviewsservice.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ReviewDto(
        val reviewId: Long? = null,
        @field:NotEmpty
        val content: String,
        @field:NotNull
        val isPositive: Boolean?,
        @field:NotNull
        val userId: Long?,
        @field:NotNull
        val filmId: Long?,
        val useful: Int? = 0
)
