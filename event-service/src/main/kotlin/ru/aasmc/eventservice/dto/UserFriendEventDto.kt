package ru.aasmc.eventservice.dto

import ru.aasmc.eventservice.enums.CommonEventOperation

data class UserFriendEventDto(
        val timestamp: Long,
        val userId: Long,
        val friendId: Long,
        val operation: CommonEventOperation
)
