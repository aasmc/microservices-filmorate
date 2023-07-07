package ru.aasmc.ratingservice.appevents

class UpdateRatingEvent(
        source: Any,
        val filmId: Long,
        val newRate: Double
)