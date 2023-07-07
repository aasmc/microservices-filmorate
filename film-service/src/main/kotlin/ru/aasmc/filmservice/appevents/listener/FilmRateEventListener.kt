package ru.aasmc.filmservice.appevents.listener

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.aasmc.filmservice.appevents.FilmRateEvent
import ru.aasmc.filmservice.service.FilmService

private val log = LoggerFactory.getLogger(FilmRateEventListener::class.java)

@Component
class FilmRateEventListener(
        private val filmService: FilmService
) {

    @EventListener
    fun handleFilmRateEvent(event: FilmRateEvent) {
        log.info("Handling Film Rate Event {}", event)
        filmService.setRateToFilm(event.filmId, event.rate)
    }

}