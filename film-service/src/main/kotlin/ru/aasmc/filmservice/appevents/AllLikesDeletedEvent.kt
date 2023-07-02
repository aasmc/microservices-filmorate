package ru.aasmc.filmservice.appevents

import org.springframework.context.ApplicationEvent

class AllLikesDeletedEvent(
    source: Any,
    val filmId: Long,
    val timeOfEvent: Long
): ApplicationEvent(source) {

}
