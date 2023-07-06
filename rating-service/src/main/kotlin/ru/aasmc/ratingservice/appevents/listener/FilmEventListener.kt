package ru.aasmc.ratingservice.appevents.listener

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.aasmc.ratingservice.appevents.DeleteAllLikesEvent
import ru.aasmc.ratingservice.appevents.FilmLikeEvent
import ru.aasmc.ratingservice.dto.EventOperation
import ru.aasmc.ratingservice.model.FilmLike
import ru.aasmc.ratingservice.model.FilmLikeId
import ru.aasmc.ratingservice.repository.FilmLikeDataRepository


private val log = LoggerFactory.getLogger(FilmEventListener::class.java)

@Component
class FilmEventListener(
        private val repository: FilmLikeDataRepository
) {

    @EventListener
    fun consumeFilmLike(event: FilmLikeEvent) {
        log.info("Consuming event {}", event)
        val id = FilmLikeId()
        id.filmId = event.dto.filmId
        id.userId = event.dto.userId
        when (event.dto.operation) {
            EventOperation.ADD -> {
                val filmLike = FilmLike(
                        id = id,
                        timestamp = event.dto.timestamp,
                        mark = event.dto.mark
                )
                repository.save(filmLike)
            }

            EventOperation.REMOVE -> {
                repository.removeFilmLikeByIdAndTimestampEquals(id, event.dto.timestamp)
            }
        }
    }

    @EventListener
    fun consumeDeleteAllLikes(event: DeleteAllLikesEvent) {
        log.info("Consuming event {}", event)
        repository.removeAllById_FilmIdAndTimestampLessThanEqual(
                event.dto.filmId,
                event.dto.timestamp
        )
    }

}