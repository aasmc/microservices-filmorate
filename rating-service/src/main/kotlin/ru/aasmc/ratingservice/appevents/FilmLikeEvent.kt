package ru.aasmc.ratingservice.appevents

import org.springframework.context.ApplicationEvent
import ru.aasmc.ratingservice.dto.DeleteAllLikesDto
import ru.aasmc.ratingservice.dto.FilmLikeDto

class FilmLikeEvent(
        source: Any,
        val dto: FilmLikeDto
): ApplicationEvent(source)

class DeleteAllLikesEvent(
        source: Any,
        val dto: DeleteAllLikesDto
): ApplicationEvent(source)
