package ru.aasmc.ratingservice.appevents.listener

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.aasmc.ratingservice.appevents.DeleteAllLikesEvent
import ru.aasmc.ratingservice.appevents.FilmLikeEvent
import ru.aasmc.ratingservice.appevents.UpdateRatingEvent
import ru.aasmc.ratingservice.dto.EventOperation
import ru.aasmc.ratingservice.dto.FilmRateDto
import ru.aasmc.ratingservice.model.FilmLike
import ru.aasmc.ratingservice.model.FilmLikeId
import ru.aasmc.ratingservice.service.FilmLikeService
import ru.aasmc.ratingservice.service.FilmRateEventMessageSender


private val log = LoggerFactory.getLogger(FilmLikeEventListener::class.java)

@Component
class FilmLikeEventListener(
        private val filmLikeService: FilmLikeService,
        private val messageSender: FilmRateEventMessageSender
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun consumeUpdateRateEvent(event: UpdateRatingEvent) {
        log.info("Consuming event {}", event)
        messageSender.sendFilmRate(FilmRateDto(
                filmId = event.filmId,
                rate = event.newRate
        ))
    }

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
                filmLikeService.saveFilmLike(filmLike)
            }

            EventOperation.REMOVE -> {
                filmLikeService.removeFilmLike(id)
            }
        }
    }

    @EventListener
    fun consumeDeleteAllLikes(event: DeleteAllLikesEvent) {
        log.info("Consuming event {}", event)
        filmLikeService.removeAllFilmLikes(
                event.dto.filmId,
                event.dto.timestamp
        )
    }

}