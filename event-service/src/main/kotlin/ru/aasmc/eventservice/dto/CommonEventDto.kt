package ru.aasmc.eventservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import ru.aasmc.eventservice.enums.CommonEventOperation
import ru.aasmc.eventservice.enums.CommonEventType

data class CommonEventDto(
        @field:Schema(
                description = "ID of the event",
                example = "1",
                type = "long"
        )
        val eventId: Long? = null,
        @field:Schema(
                description = "Epoc millis, specifying the moment the event happened."
        )
        val timestamp: Long,
        @field:Schema(
                description = "ID of the User who initiated the event."
        )
        val userId: Long,
        @field:Schema(
                description = "Specifies type of the event. FRIEND, LIKE, REVIEW",
                example = "FRIEND"
        )
        val eventType: CommonEventType,
        @field:Schema(
                description = "Specifies the operation performed when the event happened. " +
                        "REMOVE, ADD, UPDATE, REMOVE_ALL.",
                example = "ADD"
        )
        val operation: CommonEventOperation,
        @field:Schema(
                description = "ID of entity the event happened to."
        )
        val entityId: Long
)
