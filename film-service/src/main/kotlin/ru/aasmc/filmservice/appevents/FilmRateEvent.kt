package ru.aasmc.filmservice.appevents

import org.springframework.context.ApplicationEvent

class FilmRateEvent(
        source: Any,
        val filmId: Long,
        val rate: Double
): ApplicationEvent(source)