package ru.aasmc.reviewsservice.error

class ReviewServiceException(
        val code: Int,
        override val message: String
): RuntimeException(message)