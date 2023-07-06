package ru.aasmc.reviewsservice.appevents

import org.springframework.context.ApplicationEvent

class ReviewEvent(
        source: Any,
        val timestamp: Long,
        val reviewId: Long,
        val operation: EventOperation,
        val userId: Long
): ApplicationEvent(source)

enum class EventOperation {
    ADD,
    REMOVE,
    UPDATE
}