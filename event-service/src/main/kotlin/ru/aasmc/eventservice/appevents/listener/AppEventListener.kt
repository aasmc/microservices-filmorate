package ru.aasmc.eventservice.appevents.listener

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.aasmc.eventservice.appevents.DeleteUserEvent
import ru.aasmc.eventservice.appevents.FilmLikeEvent
import ru.aasmc.eventservice.appevents.ReviewEvent
import ru.aasmc.eventservice.appevents.UserFriendEvent
import ru.aasmc.eventservice.dto.CommonEventDto
import ru.aasmc.eventservice.enums.CommonEventOperation
import ru.aasmc.eventservice.enums.CommonEventType
import ru.aasmc.eventservice.service.EventService

private val log = LoggerFactory.getLogger(AppEventListener::class.java)

@Component
class AppEventListener(
        private val eventService: EventService
) {

    @EventListener
    fun consumeUserEvent(event: UserFriendEvent) {
        log.info("Consuming event: {}", event)
        if (event.dto.operation == CommonEventOperation.REMOVE_ALL) {
            eventService.deleteEventsForUserWithId(event.dto.userId)
        } else {
            eventService.saveEvent(
                    CommonEventDto(
                            timestamp = event.dto.timestamp,
                            userId = event.dto.userId,
                            eventType = CommonEventType.FRIEND,
                            operation = event.dto.operation,
                            entityId = event.dto.friendId
                    )
            )
        }
    }

    @EventListener
    fun consumeReviewEvent(event: ReviewEvent) {
        log.info("Consuming event: {}", event)
        eventService.saveEvent(
                CommonEventDto(
                        timestamp = event.dto.timestamp,
                        userId = event.dto.userid,
                        eventType = CommonEventType.REVIEW,
                        operation = event.dto.operation,
                        entityId = event.dto.reviewId
                )
        )
    }

    @EventListener
    fun consumeFilmLikeEvent(event: FilmLikeEvent) {
        log.info("Consuming event: {}", event)
        eventService.saveEvent(
                CommonEventDto(
                        timestamp = event.dto.timestamp,
                        userId = event.dto.userId,
                        eventType = CommonEventType.LIKE,
                        operation = event.dto.operation,
                        entityId = event.dto.filmId
                )
        )
    }

    @EventListener
    fun consumeDeleteUserEvent(event: DeleteUserEvent) {
        log.info("Consuming event: {}", event)
        eventService.
    }

}