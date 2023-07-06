package ru.aasmc.reviewsservice.dto

import ru.aasmc.reviewsservice.appevents.EventOperation

data class ReviewEventDto(
        val timestamp: Long,
        val reviewId: Long,
        val operation: EventOperation,
        val userId: Long
)
