package ru.aasmc.eventservice.appevents

import org.springframework.context.ApplicationEvent
import ru.aasmc.eventservice.dto.FilmLikeDto
import ru.aasmc.eventservice.dto.ReviewEventDto
import ru.aasmc.eventservice.dto.UserFriendEventDto

class FilmLikeEvent(
        source: Any,
        val dto: FilmLikeDto
): ApplicationEvent(source)

class ReviewEvent(
        source: Any,
        val dto: ReviewEventDto
): ApplicationEvent(source)

class UserFriendEvent(
        source: Any,
        val dto: UserFriendEventDto
): ApplicationEvent(source)

class DeleteUserEvent(
        source: Any,
        val userId: Long
): ApplicationEvent(source)