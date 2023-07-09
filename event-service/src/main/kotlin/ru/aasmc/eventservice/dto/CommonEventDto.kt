package ru.aasmc.eventservice.dto

import ru.aasmc.eventservice.enums.CommonEventOperation
import ru.aasmc.eventservice.enums.CommonEventType

data class CommonEventDto(
        val eventId: Long? = null,
        val timestamp: Long,
        val userId: Long,
        val eventType: CommonEventType,
        val operation: CommonEventOperation,
        val entityId: Long
)
