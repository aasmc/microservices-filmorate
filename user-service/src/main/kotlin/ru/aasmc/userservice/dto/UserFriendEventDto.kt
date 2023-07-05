package ru.aasmc.userservice.dto

import ru.aasmc.userservice.appevents.EventOperation

data class UserFriendEventDto(
        val timestamp: Long,
        val userId: Long,
        val friendId: Long,
        val operation: EventOperation
)
