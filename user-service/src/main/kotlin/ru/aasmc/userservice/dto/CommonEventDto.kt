package ru.aasmc.userservice.dto

data class CommonEventDto(
        val eventId: Long,
        val timestamp: Long,
        val userId: Long,
        val eventType: CommonEventType,
        val operation: CommonEventOperation,
        val entityId: Long
)

enum class CommonEventType {
    LIKE,
    REVIEW,
    FRIEND
}

enum class CommonEventOperation {
    REMOVE,
    ADD,
    UPDATE,
    REMOVE_ALL
}