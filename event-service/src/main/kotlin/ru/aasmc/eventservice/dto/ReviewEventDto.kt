package ru.aasmc.eventservice.dto

import ru.aasmc.eventservice.enums.CommonEventOperation

data class ReviewEventDto(
        val timestamp: Long,
        val reviewId: Long,
        val operation: CommonEventOperation,
        val userid: Long
)
