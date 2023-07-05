package ru.aasmc.userservice.appevents

import org.springframework.context.ApplicationEvent

class UserFriendEvent(
        source: Any,
        val timestamp: Long,
        val userId: Long,
        val operation: EventOperation,
        val friendId: Long
): ApplicationEvent(source)

enum class EventOperation {
    ADD,
    REMOVE,
    REMOVE_ALL
}
